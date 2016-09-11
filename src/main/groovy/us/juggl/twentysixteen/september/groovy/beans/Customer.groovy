package us.juggl.twentysixteen.september.groovy.beans

/**
 * This is a Bean in Groovy. No getters, setters, builders, constructors required.<br />
 * <br />
 * Using <b>Named Argument Constructor</b><br />
 * &nbsp;&nbsp;&nbsp;&nbsp;def cust = new Customer(givenName: 'John', familyName: 'Smith', id: 1)
 */
class Customer {

    String givenName = ''

    String familyName = ''

    Integer id = 0

    Address address;

    String fullName() {
        // Make not that there is no `return` statement. In groovy, the last value is implied to be returned
        "${givenName} ${familyName}".toString()

        // Same as: return "${givenName} ${familyName}".toString()
    }
}
