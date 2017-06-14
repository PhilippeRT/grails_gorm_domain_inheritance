package grails_gorm_domain_inheritance

import org.prt.User


class BootStrap {

    def init = { servletContext ->
        def ad = new User(username: 'admin', password: 'admin').save(flush: true)
    }
    def destroy = {
    }
}
