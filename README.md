# Maven Framework for easily submitting ontology matching systems and benchmarks to SEALS and HOBBIT

Prerequisites for Hobbit is a working docker installation ([download docker](https://www.docker.com/get-docker))

### TL;DR
1. clone or download this repository
1. create hobbit account and gitlab access token
1. adjust settings in pom.xml to your needs
1. implement your matcher (extend MatcherURL class and implement method ```URL match(URL source, URL target, URL inputAlignment)```
1. execute ```mvn deploy``` to create seals zip and deploy docker image to hobbit server
    - if you only execute ```mvn install``` it will create seals zip and hobbit docker image locally
    - if you execute ```mvn package``` only seals zip will be created
1. the seals zip can be found in the target folder and the hobbit docker image in the usual place (```docker images```)


### In more detail

- for Hobbit submission
    - create a user account
        - open [http://master.project-hobbit.eu/](http://master.project-hobbit.eu/)  and click on ```Register```
        - user name should be the first part (local part - everything before the @) of your mail address
        - mail: `max.power@example.org` then user name should be `max.power`
        - more information at [the  hobbit wiki page](https://github.com/hobbit-project/platform/wiki/User-registration)
    - update settings in gitlab (in Hobbit every matcher corresponds to a gitlab project)
      - go to page [http://git.project-hobbit.eu](http://git.project-hobbit.eu) and log in (same account as for the platform itself)
      - click on the upper right user icon and choose `settings`
      - create a Personal Access Token (click on `Access Tokens` give it a name and choose only the `api` scope)
      - use this access token and you username and password to create the settings file (see the pom.xml)
- adjust pom.xml to your needs
    - definitly change the following:
        - groupId and artifactId (only artifactId is used to identify the matcher -> make it unique)
        - oaei.mainClass: set it to the fully qualified path to the matcher
        - benchmarks: change the benchmarks to the ones your system can deal with
        - create a settings file with username, password and access_token
- implement your matcher (choose one of the options)
    - extend MatcherURL class and implement method ```URL match(URL source, URL target, URL inputAlignment)```
    - extend MatcherString class and implement method ```String match(String source, String target, String inputAlignment)```
    - extend MatcherJena class and implement method ```Mapping match(OntModel source, OntModel target, Mapping inputAlignment, Properties p)```
    - all of these interfaces  
        - gets two ontologies/knowledge bases (source and target as URL, String or OntModel)
        - gets one input alignment in [alignment format](http://alignapi.gforge.inria.fr/format.html) (inputAlignment as URL, String or already parsed as Mapping)
        - returns an alignment in [alignment format](http://alignapi.gforge.inria.fr/format.html) as URL (the url of the path of a temporary file), String or Mapping object
    - more possibilities (OWL API etc) will be added in the future (you can also use it now and parse the input and output yourself)
- build your matcher
    - execute maven goals from command line or from any IDE
    - ```mvn package``` will only build seals zip
    - ```mvn install``` will create seals zip and hobbit docker image locally
    - ```mvn deploy``` will create seals zip and deploy docker image to hobbit server
- submit your matcher
    - for SEALS upload the generated seals file ```{artifactId}-{version}-seals.zip``` in the target folder
    - for Hobbit call ```mvn deploy```

### Evaluate your matcher

- you can start an experiment in hobbit online platform
    - go to page [http://master.project-hobbit.eu/](http://master.project-hobbit.eu/), log in and choose `Benchmarks`
    - select the benchmark you want to use
    - select the system you want to use
    - (optionally) specify configuration parameters and click on `submit`
    - click on the Hobbit ID in the pop up to see the results (reload the page if it is not finished)
    - more information at [the  hobbit wiki page 'Experiments' ](https://github.com/hobbit-project/platform/wiki/Experiments)
- or you can evaluate the matcher locally
    - have a look at the ```EvaluateMatcher``` class in the test package
