import groovy.json.JsonOutput;
import groovy.json.JsonSlurper;

def condition = "good";
def severity = "good";
def summary = "";
def detail = "";

// def prompt = INPUTS["RTR_PROMPT"] ?: "";
def prompt = /[\#,\>]/;
def vipName = assertInput("VIP_NAME");


def timeout = INPUTS["TIMEOUT"] ?: 10;
def finalVipState = ""

try
{
	detail += SESSIONS.getClass()
	def sessionId = assertInput("SESSION_ID");
	def conn = sessionId ? SESSIONS.get(sessionId) : SESSIONS.get()
	if(!conn)
	{
		throw new Exception("SESSION(connection) object was not found. $sessionId");
	}

	// Execute command
	def execute = new ExecuteFW(conn);
	
	execute.sendAndExpect("bash", prompt, 5, true);
	def cmd = "sh save | grep " + vipName
	
	timeout = timeout.toInteger();
	def result = execute.sendAndExpect(cmd, prompt, timeout, true);
	// detail += result
	
	result.eachLine{it->
		if(it=~ /^add\slb/){
			def matcher = it =~ /-state\s(?<state>(\w+))/
			if(matcher.find()){
				detail += it + "\n"
				finalVipState += matcher.group("state")
			}	
		}
	}
	if(finalVipState == "DISABLED"){
		condition = "bad"	
	}
	
}
catch(Exception e)
{
    condition = "bad";
    severity = "critical";
    summary = "Encountered exception: " + e.getMessage();
    detail += "Encountered the following exception:";
    detail += "\n" + e.toString();
    for(line in e.getStackTrace())
    {
        detail += "\n\t" + line
    }
}

def contentReturn = [:]
contentReturn.condition = condition;
contentReturn.severity = severity;
contentReturn.summary = summary;
contentReturn.detail = detail;

contentReturn.finalVipState = finalVipState;


INPUTS["CONTENT_RETURN"] = contentReturn;


def assertInput(inputsName)
{
    if(!INPUTS[inputsName]) throw new GroovyRuntimeException("Missing required input ${inputsName}");
    return INPUTS[inputsName];
}

class ExecuteFW
{
	def conn;
	def commandResponseLog = [:]
	
	public ExecuteFW(conn)
	{		
		this.conn = conn;
	}
	
	public sendAndExpect(command, prompt, timeout)
	{
		return sendAndExpect(command, prompt, timeout, false);
	}
	
	public sendAndExpect(command, prompt, timeout, ignoreErrorHandling)
	{
		// if you get null results, try setting ignoreErrorHandling to true
		if(commandResponseLog.containsKey(command))
		{
			return commandResponseLog[command];
		}
		else
		{		
			conn.send(command);
			Thread.sleep(100);
			def result = conn.expect(prompt,timeout);
			if(!ignoreErrorHandling)
			{
				def whileLoopCount = 0;
				while(!(result ==~ /(?s).*>\s*$/) && whileLoopCount < 20)
				{				
					whileLoopCount++
					result += conn.expect(prompt,timeout);
				}
				
				if(result.count('>') > 1)
				{
					result = result.find(/(?ms).+(>.+>$)/)
				}
			}
			commandResponseLog[command] = result;			
			return result;
		}
	}
	
	public getCommandResponseLog()
	{
		return commandResponseLog;	
	}	
}
class ExecuteFW2
{
	def conn;
	def commandCache;
	def commandCacheLog = [:];	
	def commandResponseLog = [:]
	
	public ExecuteFW2(conn, commandCache)
	{		
		this.conn = conn;
		this.commandCache = commandCache;
	}
	
	public sendAndExpect(command, prompt, timeout)
	{
		return sendAndExpect(command, prompt, timeout, false);
	}
	
	public sendAndExpect(command, prompt, timeout, ignoreErrorHandling)
	{
		// When a command is entered incorrectly (syntax error) the SRX device responds with
		// messages related to invalid syntax. This conflicts with standard expect functionality.
		// To work aournd this there is logic (regex below) to only show the recent message between
		// the last two > prompts. Sometimes this workaround causes other parsing issues, which is why
		// syntax handling logic can be disabled with the method parameter "ignoreErrorHandling"
		
		if(commandCache.containsKey(command.trim()))
		{
			commandResponseLog[command.trim()] = "===CACHED COMMAND===\n" + commandCache[command.trim()];
			return commandCache[command.trim()];
		}
		else
		{	
			
			def joinedPrompts = prompt;
			if(prompt instanceof List)
			{
				joinedPrompts = "(" + prompt.join("|") + ")";
			}
	
			conn.send(command);
			Thread.sleep(100);
			println("Sent command: $command");
			def result = conn.expect(prompt,timeout);
			println("Last Expect:" + result)
			def whileLoopCount = 0;
			while(!(result ==~ /(?s).*$joinedPrompts\s*$/) && whileLoopCount < 20)
			{				
				whileLoopCount++
				// println("While Loop Count:" + whileLoopCount);
				result += conn.expect(prompt,timeout);
				// println("Last Expect:" + result)
			}
			
			if(result.count('#') > 1)
			{
				result = result.find(/(?ms).+(#.+#$)/) //Only grab the last occurence of text between two # characters
			}
			
			commandResponseLog[command.trim()] = result;
			commandCache[command.trim()] = result;			
			return result;
			
		}
	}
	
	public getCommandResponseLog()
	{
		return commandResponseLog;	
	}	

	public getCommandCache()
	{
		return commandCache;	
	}		
}






















command:
> sh save | grep <Backend server Name>
> sh save | grep 10.237.250.35:443
add service 10.237.250.35:443 10.237.250.35 SSL_BRIDGE 443 -gslb NONE -maxClient 0 -maxReq 0 -cip DISABLED -usip NO -useproxyport YES -sp ON -cltTimeout 180 -svrTimeout 360 -CKA NO -TCPB NO -CMP NO -state DISABLED -comment BDC8RFISOSIAP01 -devno 44892160 ## exclude “-devno 44892160” as backout plan
bind lb vserver bdoc-ose-wildcard-443-sslbridge-staging 10.237.250.35:443
## Comments : if multipe lines like this, don’t put in the install plan
bind service 10.237.250.35:443 -monitorName ocp-router -devno 52527104
> 
result:
server 10.237.250.35:443 is bind only VIP bdoc-ose-wildcard-443-sslbridge-staging
state - disabled 

command:
> sh save | grep 10.237.250.35:80
add service 10.237.250.35:80 10.237.250.35 HTTP 80 -gslb NONE -maxClient 0 -maxReq 0 -cip DISABLED -usip NO -useproxyport YES -sp ON -cltTimeout 180 -svrTimeout 360 -CKA NO -TCPB NO -CMP YES -state DISABLED -comment BDC8RFISOSIAP01 -devno 44990464
bind lb vserver bdoc-ose-wildcard-80-staging 10.237.250.35:80
bind service 10.237.250.35:80 -monitorName ocp-router -devno 52428800
>
result:
server 10.237.250.35:80 is bind only VIP bdoc-ose-wildcard-80-staging
state - disabled 
