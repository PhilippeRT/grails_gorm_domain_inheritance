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
