import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Clase de pruebas compartida para ciclo 2 de SlotMachine.
 * Cada caso incluye en su nombre iniciales en orden alfabetico:
 * As = Alvarez Silva, Ga = Gonzalez Alba. Las pruebas se ejecutan en
 * modo invisible y cada caso prepara lo que necesita.
 *
 * @author Sergio Luis Gonzalez Alba (Ga), Juan Andres Alvarez Silva (As)
 * @version 3.0
 */
public class SlotMachineCC2Test{

    private SlotMachine machine;

    @Before
    public void setUp(){
        machine = new SlotMachine();
    }

    /**
     * Al intercambiar dos ruedas, la maquina debe seguir teniendo el mismo
     * numero de ruedas (swap no debe agregar ni perder ruedas).
     */
    @Test
    public void accordingAsGaShouldKeepTheSameNumberOfWheelsAfterASwap(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.swap(1, 2);
        assertTrue(machine.ok());
        assertEquals(2, machine.configuration().length);
    }

    /**
     * Una rueda fija no debe cambiar su simbolo al frente, ni con
     * spin(wheel) ni con spin(wheel,steps).
     */
    @Test
    public void accordingAsGaShouldNotChangeALockedWheelWhenSpinning(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(1, "red");
        machine.lock(1);
        machine.spin(1);
        machine.spin(1, 10);
        assertEquals("red", machine.configuration()[0]);
    }
}
