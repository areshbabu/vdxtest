Assumptions:
1. git-repo-config direcotry is used to checkin the config files to read it from github
2. git-config directory polls the git url and fetches the config files from git and it will be proceesed by the file processor
3. Considering there is only one file in  each app directory, I have not written the github rest api call to fetch the files instead manually copied the feed from github using the raw url due to time constraint. Hope it should be ok
4. Exceptions are not handled properly due to time constraint

Application Flow:
1. Client - ConfigController has only one rest api call to fetch the property key-value information using the below rest api call
   REST - http://localhost:7777/conf-server/getConfig?appName=app1&key=config.env
   Output - {
    "key": "config.env",
    "value": "dev_local1"
}

2. Scheduler - ConfigPoller written which runs the scheduled  job for every second and triggers the FileSystemPoller and GitPoller to fetch the latest files continuosly
3. There are 2 pollers written GitPoller.java and FileSystemPoller.java
   GitPoller - Polls the git url and fetches the property files from git url 
   FileSystemPoller - Reads the files from the file system
4. FileProcessorService - Reads the files and loads the data into the Redis in-memory database by using Hashing datastructure.
5. Redis Stores the data in the format of key-value pair.
   Key - AppName + PropertyFile Key name
   Value - Value mentioned in the property file
   Eg: app1/config-dev.properties
   config.env=dev_local1
   Key = app1+config.env
   value = dev_local1
6. For the first get call, redis fetches data from the database and caches the data, from the next subsequence call the data will be fetched from the cache instead of triggering    db call for every request
   @Cacheable(key="#id", value = "Config")
	 public ConfigEntity findConfigById(String id) {
   }
7. For any update on the existing key, the value will be updated in both database as well as cache since the CachePut is configured while storing the data in the database.
  @CachePut(key="#entity.id", value = "Config")
	public ConfigEntity saveOrUpdate(ConfigEntity entity) {
  }

Build & Deployment:
1. ConfigManageServerApplication.java to run the server and trigger the below GET call to check the output
   http://localhost:7777/conf-server/getConfig?appName=app1&key=config.env
   
2. Run the test case to trigger the rest call and verify the data. Currently one test case written and there are many more test cases yet to be written.
