# grails_gorm_domain_inheritance
Having a base domain class to fill default columns

The base project was to have 2 columns createdBy and lastUpdatedBy filled automatically by the Spring Security plug-in.

To achieved this :
## 1. Add the Spring Security plung-in in build.gradle
``` groovy
buildscript {
    repositories {
        mavenLocal()
        maven { url "https://repo.grails.org/grails/core" }
    }
    dependencies {
        classpath "org.grails:grails-gradle-plugin:$grailsVersion"
        classpath "com.bertramlabs.plugins:asset-pipeline-gradle:2.14.1"
        classpath "org.grails.plugins:hibernate5:${gormVersion-".RELEASE"}"
    }
}

version "0.1"
group "grails_gorm_domain_inheritance"

apply plugin:"eclipse"
apply plugin:"idea"
apply plugin:"war"
apply plugin:"org.grails.grails-web"
apply plugin:"org.grails.grails-gsp"
apply plugin:"asset-pipeline"

repositories {
    mavenLocal()
    maven { url "https://repo.grails.org/grails/core" }
}

dependencies {
    compile "org.springframework.boot:spring-boot-starter-logging"
    compile "org.springframework.boot:spring-boot-autoconfigure"
    compile "org.grails:grails-core"
    compile "org.springframework.boot:spring-boot-starter-actuator"
    compile "org.springframework.boot:spring-boot-starter-tomcat"
    compile "org.grails:grails-dependencies"
    compile "org.grails:grails-web-boot"
    compile "org.grails.plugins:cache"
    compile "org.grails.plugins:scaffolding"
    compile "org.grails.plugins:hibernate5"
    compile "org.hibernate:hibernate-core:5.1.3.Final"
    compile "org.hibernate:hibernate-ehcache:5.1.3.Final"
    console "org.grails:grails-console"
    profile "org.grails.profiles:web"
    runtime "com.bertramlabs.plugins:asset-pipeline-grails:2.14.1"
    runtime "com.h2database:h2"
    testCompile "org.grails:grails-plugin-testing"
    testCompile "org.grails.plugins:geb"
    testRuntime "org.seleniumhq.selenium:selenium-htmlunit-driver:2.47.1"
    testRuntime "net.sourceforge.htmlunit:htmlunit:2.18"

    compile 'org.grails.plugins:spring-security-core:3.1.2'
}

bootRun {
    jvmArgs('-Dspring.output.ansi.enabled=always')
    addResources = true
}


assets {
    minifyJs = true
    minifyCss = true
}
```

## 2. Create an abstract base domain class in src/main/groovy
``` groovy
package org.prt

/**
 * Created by prt on 14/06/17.
 */
abstract class BaseDomain {

    static constraints = {
        createdBy nullable: true
        lastUpdatedBy nullable: true
    }

    Date lastUpdated
    Date dateCreated
    String lastUpdatedBy
    String createdBy

    def beforeInsert() {
        createdBy = springSecurityService.principal.username
        lastUpdatedBy = springSecurityService.principal.username
    }

    def beforeUpdate() {
        lastUpdatedBy = springSecurityService.principal.username
    }

}

```

## 3. Create a domain class inheriting the base domain class and inject the service in.
``` groovy
package org.prt

import grails.plugin.springsecurity.SpringSecurityService

class MyDomain extends BaseDomain {

    static constraints = {
    }

    transient SpringSecurityService springSecurityService
    String code
    String description

}
```
## 4. Init the Spring Security Plugin
``` bash
grails s2-quickstart com.yourapp User Role
```

To test, run the command gradle bootRun, in the index page click the MyDomain controller.
After having fill the user (admin/admin) create an object.
After having validated, the createdBy and lastUpdatedBy are filled.
