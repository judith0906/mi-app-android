import org.junit.Assert.*
import org.junit.Test

class UtilsTest {

    @Test
    fun mayorEdad_ok() {
        assertTrue(esMayorEdad(18))
    }

    @Test
    fun suma_ok() {
        assertEquals(4, suma(2, 2))
    }
}