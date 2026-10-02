import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Clase de pruebas compartida para ciclo 1 de SlotMachine.
 * Cada caso incluye en su nombre iniciales en orden alfabetico:
 * As = Alvarez Silva, Ga = Gonzalez Alba. Las pruebas se ejecutan en
 * modo invisible y cada caso prepara lo que necesita.
 * Primera parte pruebas propias, segunda parte las tomadas de
 * la wiki del curso, adaptadas a nuestro SlotMachine.
 *
 * @author Sergio Gonzalez Alba (Ga), Juan Andres Alvarez Silva (As)
 * @version 2.0
 */
public class SlotMachineCC1Test{

    private SlotMachine machine;

    @Before
    public void setUp(){
        machine = new SlotMachine();
    }

    /**
     * Un simbolo que no esta en el catalogo no se puede colocar en una
     * rueda: la operacion falla y la rueda sigue sin simbolo al frente.
     */
    @Test
    public void accordingAsGaShouldNotPlaceASymbolThatIsNotInTheCatalog(){
        machine.addWheel(1);
        machine.placeSymbol(1, "red");
        assertFalse(machine.ok());
        assertNull(machine.configuration()[0]);
    }

    /**
     * Hay premio cuando todas las ruedas muestran el mismo color, y deja
     * de haberlo si una rueda cambia de color.
     */
    @Test
    public void accordingAsGaShouldBeJackpotOnlyWhileAllWheelsShowTheSameColor(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "red");
        assertTrue(machine.isJackpot());
        machine.placeSymbol(2, "blue");
        assertFalse(machine.isJackpot());
    }

    // ---------------------------------------------------------------
    // PRUEBAS WIKI
    // ---------------------------------------------------------------

    /**
     * Grupo GomezCarrero: insertar dos ruedas en la primera posicion.
     * (Se cuenta con configuration().length en vez de wheels().)
     */
    @Test
    public void testAddWheelInFirstPosition(){
        machine.addWheel(1);
        machine.addWheel(1);
        assertEquals(2, machine.configuration().length);
        assertTrue(machine.ok());
    }

    /**
     * Grupo GomezCarrero: una posicion mayor al maximo se ajusta.
     */
    @Test
    public void testAddWheelPositionAboveMaximum(){
        machine.addWheel(100);
        assertEquals(1, machine.configuration().length);
        assertTrue(machine.ok());
    }

    /**
     * Grupo BustosZorro: un simbolo existente se puede eliminar.
     */
    @Test
    public void accordingZGBCShouldDeleteExistingSymbol(){
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.delSymbol("red");
        assertTrue(machine.ok());
    }

    /**
     * Grupo BustosZorro: un simbolo que no existe no se puede eliminar.
     */
    @Test
    public void accordingZGBCShouldNotDeleteMissingSymbol(){
        machine.addWheel(1);
        machine.delSymbol("green");
        assertFalse(machine.ok());
    }
}