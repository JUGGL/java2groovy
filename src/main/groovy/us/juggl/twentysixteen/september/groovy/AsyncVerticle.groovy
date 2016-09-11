// What? No semicolons? You can take 'em or leave 'em in Groovy, though the style guide recommends against them.
package us.juggl.twentysixteen.september.groovy

// Ooooh! Import aliases!!!
import io.vertx.core.AbstractVerticle as AV

// All classes are public by default, unlike package private in Java
class AsyncVerticle extends AV {

    // All methods are "public" by default instead of package private in Java

    public static final String ADDRESS = "us.juggl.endpoint1"

    void start() throws Exception {
        // Look ma, no parenthesis around method arguments?!?! - I am NOT a fan, but you are free to make your own choices
        vertx.eventBus.consumer ADDRESS, { msg ->
            // This is a Groovy Closure instead of a Java 8 Lambda
            // Notice that the curly brace is BEFORE the lambda argument of `msg`

            String body;

            // This is the equivalent of doing a null check AND a zero length check in Java!
            if (msg.body()) {     // Java equivalent::: if (msg.body()!=null && msg.body().length() > 0)
                body = msg.body()
            } else {
                body = \
 $/
    Slashy strings can contain almost anything, including variables for
    interpolation (${ADDRESS}), $ (dollar) signs, Forward slashes (/), backslashes (\),
    and almost anything else you might normally need escape characters for!
/$
            }
            msg.reply "The Message body recieved was: ${body}".toString()   // A Groovy GString using in-line interpolation
        }
    }
}