# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Caching Data with Spring](https://spring.io/guides/gs/caching/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Handling Form Submission](https://spring.io/guides/gs/handling-form-submission/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)


# Some common issue
1. remember pyinstaller installed executable on MacOS can't work on ubuntu or other linux
2. if encounter Pyinstaller libmkl_intel_thread.so: undefined symbol: omp_get_num_procs, need to install some package: `conda install nomkl numpy scipy`
2.1 see reference: https://stackoverflow.com/questions/48609117/pyinstaller-libmkl-intel-thread-so-undefined-symbol-omp-get-num-procs and https://github.com/conda-forge/numpy-feedstock/issues/108
