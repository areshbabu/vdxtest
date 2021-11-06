Assumptions:
1. git-repo-config direcotry is used to checkin the config files to read it from github
2. git-config directory polls the git url and fetches the config files from git and it will be proceesed by the file processor
3. Considering there is only one file in  each app directory, I have not written the github rest api call to fetch the files instead manually copied the feed from github using the raw url due to time constraint. Hope it should be ok

Application Flow:
