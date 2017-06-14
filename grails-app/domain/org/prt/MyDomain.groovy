package org.prt

import grails.plugin.springsecurity.SpringSecurityService

class MyDomain extends BaseDomain {

    static constraints = {
    }

    transient SpringSecurityService springSecurityService
    String code
    String description

}
