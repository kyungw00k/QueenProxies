# QueenProxies API

In brief, try to implement as same as [King Proxies API Documentation](https://kingproxies.com/docs)

## API
Get a list of proxies

|Method|Route|Response|
|------|--|--|
|GET|`/proxies`|JSON|


#### Parameters
|Name|	Type|	Value|	Description|
|----|------|------|------|
|key|	string|	Register to get your free API key|	**REQUIRED** <br />Your API key.|
|type|	string|	elite, anonymous, or transparent|	Add this to retrieve only proxies with a type. Comma-separate multiple values to search with OR condition. <br /> DEFAULT: All types|
|protocols|	string|	http, socks4, or socks5|	Add this to retrieve only proxies with a specific protocol. Comma-separate multiple values to search with OR condition.<br />DEFAULT: All protocols|
|alive|	boolean|	true/false, 1/0, yes/no|	Only retrieve proxies that were tested to be working within the last 5 minutes.<br />DEFAULT: true
|countryCode|	string|		|Add this to retrieve only proxies at this location. Comma-separate multiple values to search with OR condition.<br />DEFAULT: All countries|

#### Response example

```
/proxies?countryCode=US
```
```
{
  "data":{
    "proxies":[
      {
        "ip":"0.0.0.0",
        "port":1499,
        "countryCode":"US",
        "region":"Georgia",
        "city":"Atlanta",
        "type":"elite",
        "protocols":["socks4","socks5"],
        "alive":true
      },
      {
        "ip":"0.0.0.0",
        "port":8938,
        "countryCode":"US",
        "region":"Georgia",
        "city":"Duluth",
        "type":"elite",
        "protocols":["socks5"],
        "alive":true
      }
    ],
    "filters":{
      "countryCode":"US"
    }
  },
  "requestId":"xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"
}
```

#### Response object

|Name|	Type|	Description|
|----|------|------------|
|data|	object|	Requested data|
|requestId|	string|	Unique 36-character string, identifying the request|
|message|	string|	Error or informational message|

#### data object

|Name	|Type|	Description|
|-----|----|-------------|
|proxies|	array	|Proxies matching requested filter conditions|
|filters|	object|	Parameters that are common to all proxies|

#### Proxy object

|Name|	Type|	Description|
|----|------|------------|
|ip|	string|	IP address|
|port|	integer|	Port|
|countryCode|	string|	Country code|
|region|	string|	Region|
|city|	string|	City|
|type|	string|	Type|
|protocols|	array|	List of protocols this proxy supports|
|alive|	boolean|	True if the proxy was working when last tested|
