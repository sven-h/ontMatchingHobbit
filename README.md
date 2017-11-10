# ontMatchingHobbit

# Using Hobbit for Ontology Matching

Prerequisites:
 - installed docker ()[https://www.docker.com/get-docker](https://www.docker.com/get-docker))
 - (optional) SSH keys

1. create a user account (also described at [https://github.com/hobbit-project/platform/wiki/User-registration](https://github.com/hobbit-project/platform/wiki/User-registration) )
    - go to page [http://master.project-hobbit.eu/](http://master.project-hobbit.eu/) and click on `Register`
    - user name should be the first part (local part - everything before the @) of your mail address
        - mail: `max.power@example.org` then user name should be `max.power`
2. update settings in gitlab
    - go to page [http://git.project-hobbit.eu](http://git.project-hobbit.eu) and log in (same account as for the platform itself)
    - click on the upper right user icon and choose `settings`
        - create a Personal Access Token (click on `Access Tokens` give it a name and choose only the `api` scope) to push code/docker image via HTTPS ([further information about scopes](https://docs.gitlab.com/ce/user/profile/personal_access_tokens.html#limiting-scopes-of-a-personal-access-token)) - (easier option)
        - OR
        - add your SSH key by clicking on `SSH Keys` to push code/docker image via SSH
3. create a project in the gitlab instance
    - click on the `+` button near the search bar on the top right and select `New project`
    - choose the project name (it should have the same name as the system) and select the `Private` visibility level
4. build/push a docker image  ([https://github.com/hobbit-project/platform/wiki/Push-a-docker-image](https://github.com/hobbit-project/platform/wiki/Push-a-docker-image))
    - you will push your local docker image to a remote docker registry
    - the URL of the registry can be found at the `Registry` page (left menu of the project page)
    - (the URL is normally `git.project-hobbit.eu:4567/{username}/{projectname}` where all letters are automatically lowercased)
    - `docker build -t git.project-hobbit.eu:4567/{username}/{projectname} .`
    - `docker login git.project-hobbit.eu:4567`
    - `docker push git.project-hobbit.eu:4567/{username}/{projectname}`
5. create meta data (see also [help page of hobbit](https://github.com/hobbit-project/platform/wiki/System-meta-data-file))
    - the hobbit system needs a meta data file on the root directory of the project-hobbit
    - the file name should be `system.ttl` and contains
        - an artificial URI for the system
        - a label
        - a comment
        - an image name which points to the gitlab docker image
        - and which benchmark it implements (following benchmark URIs for ontology matching)
            - Spatial Benchmark: `http://w3id.org/bench#SpatialAPI`
            - Linking Benchmark: `http://w3id.org/bench#LinkingAPI`
    - the minimal version of a `system.ttl` file is
```
@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .
@prefix hobbit: <http://w3id.org/hobbit/vocab#> .
<{systemURL}> a  hobbit:SystemInstance;
	rdfs:label	"{label}"@en;
	rdfs:comment	"{comment}"@en;
	hobbit:imageName "git.project-hobbit.eu:4567/{username}/{projectname}";
	hobbit:implementsAPI <http://benchmark.org/MyNewBenchmark/BenchmarkApi> .
```

6. start an experiment (see also [https://github.com/hobbit-project/platform/wiki/Experiments](https://github.com/hobbit-project/platform/wiki/Experiments))
    - go to page [http://master.project-hobbit.eu/](http://master.project-hobbit.eu/), log in and choose `Benchmarks`
    - select the benchmark you want to use
    - select the system you want to use
    - (optionally) specify configuration parameters and click on `submit`
    - click on the Hobbit ID in the pop up to see the results (reload the page if it is not finished)

7. a more advanced version of the `system.ttl` can look like
```
@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .
@prefix hobbit: <http://w3id.org/hobbit/vocab#> .
@prefix sys: <http://w3id.org/system#> .
@prefix bench: <http://w3id.org/bench#> .

sys:{systemURL} a  hobbit:SystemInstance ;
	rdfs:label	"{label}"@en;
	rdfs:comment	"{comment}" "@en;
	hobbit:implementsAPI	bench:SpatialAPI;
	hobbit:imageName "git.project-hobbit.eu:4567/{username}/{projectname}" ;
	hobbit:instanceOf sys:{generalSystemURL}  .

sys:{generalSystemURL} a hobbit:System ;
    rdfs:label	"{generalSystemLabel}"@en;
    rdfs:comment "{generalSystemComment}"@en .
```

steps 3-5 can be automated eventually by Gitlab api
 - [https://docs.gitlab.com/ee/api/projects.html](https://docs.gitlab.com/ee/api/projects.html)
 - [https://docs.gitlab.com/ee/api/repository_files.html](https://docs.gitlab.com/ee/api/repository_files.html)
