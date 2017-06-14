# grails_gorm_domain_inheritance
Having a base domain class to fill default columns

The base project was to have 2 columns createdBy and lastUpdatedBy filled automatically by the Spring Security plug-in.

To achieved this :
- modify the default gorm configuration (since grails 3.2.9) by setting grails.gorm.autowire to true
- create an abstract class BaseDomain class with the common columns in the src folder. The service CAN'T be injected because it is not a domain class.
- create a domain class inheriting the base domain class and inject the service in

Don't forget to init the Spring Security plugin (s2-quickstart com.yourapp User Role)