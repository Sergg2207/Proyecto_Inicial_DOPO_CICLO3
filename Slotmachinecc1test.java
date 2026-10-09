import static org.junit.Assert.*;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

/**
 * Clase de pruebas compartida para ciclo 1 de SlotMachine.
 * Cada caso incluye en su nombre iniciales en orden alfabetico:
 * As = Alvarez Silva, Ga = Gonzalez Alba. Las pruebas se ejecutan en
 * modo invisible y cada caso prepara lo que necesita
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

    // Las siguientes son las pruebas de la wiki que aparecen en el documento
    // "Errores por revisar_WIKI" (las que fallaban o no compilaban).

    /**
     * Grupo Carvajal- Largo.
     * Tema del documento (seccion 1): addSymbol y que exige placeSymbol.
     * Resultado reportado en el documento: Falla en nuestro proyecto: expected blue but was red
     */
    @Test
    public void accordingCxLxShouldAdvanceToNextSymbol(){
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel(1);
        machine.placeSymbol(1, "red");
        machine.spin(1);
        assertEquals("blue", machine.configuration()[0]);
        assertTrue(machine.ok());
    }

    /**
     * Grupo 06 CuervoC -- InfanteC.
     * Tema del documento (seccion 1): addSymbol y que exige placeSymbol.
     * Resultado reportado en el documento: Falla en nuestro proyecto: expected 2 but was 0
     * Adaptacion: se usa el atributo machine del setUp.
     */
    @Test
    public void accordingCcIcshouldCalculateDistinctSymbolsCorrectly(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "red");
        machine.placeSymbol(3, "green");
        assertEquals(2, machine.distinctSymbols());
        assertTrue(machine.ok());
    }

    /**
     * Grupo Coronado - Horta.
     * Tema del documento (seccion 6): reglas que no estan en el enunciado.
     * Resultado reportado en el documento: Falla en nuestro proyecto: expected false
     */
    @Test
    public void accordingCgHnIsJackpotShouldBeFalseWithOnlyOneWheelEvenIfSymbolIsSet(){
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.placeSymbol(1, "red");
        assertFalse(machine.isJackpot());
    }

    /**
     * Grupo 06 CuervoC -- InfanteC.
     * Tema del documento (seccion 6): reglas que no estan en el enunciado.
     * Resultado reportado en el documento: Falla en nuestro proyecto: expected false
     * Adaptacion: se usa el atributo machine del setUp.
     */
    @Test
    public void accordingCcIcshouldNotExceedMaximumWheelsLimit(){
        for (int i = 1; i <= 10; i++) {
            machine.addWheel(i);
        }
        assertFalse(machine.ok());
        assertEquals(9, machine.configuration().length);
    }

    /**
     * Grupo 06 CuervoC -- InfanteC.
     * Tema del documento (seccion 6): reglas que no estan en el enunciado.
     * Resultado reportado en el documento: Falla en nuestro proyecto: expected false
     * Adaptacion: se usa el atributo machine del setUp.
     */
    @Test
    public void accordingCcIcshouldNotAllowOperationsWhenNoWheelsExist(){
        machine.delWheel(1);
        assertFalse(machine.ok());
        machine.addSymbol(1, "red");
        assertFalse(machine.ok());
        machine.spin(1);
        assertFalse(machine.ok());
    }

    /**
     * Grupo-02 ForeroJ-SalinasS.
     * Tema del documento (seccion 7): prueba sin setUp en la wiki.
     * Resultado reportado en el documento: Falla en nuestro proyecto: java.lang.ArrayIndexOutOfBoundsException: Index 0 out of bounds for length 0
     * Adaptacion: se usa el atributo machine del setUp; la wiki asume 3 ruedas y el simbolo "black" por defecto: se agregan al inicio.
     */
    @Test
    public void accordingFmSnShouldShowCorrectConfiguration(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
        machine.addSymbol(1, "black");
        machine.addSymbol(1, "red"); // Add a new symbol [black,red] - With black being the default.
        machine.addSymbol(1, "green"); // Add another symbol [black,green,red].
        machine.placeSymbol(1, "red"); // Change the first wheel's symbol to red.
        machine.placeSymbol(2, "green"); // Change the second wheel's symbol to green.
        machine.placeSymbol(3, "black"); // Change the third wheel's symbol to black.
        String[] config = machine.configuration(); // Create config with the machine's current configuration.
        assertEquals("red", config[0]); // Verify that positions match.
        assertEquals("green", config[1]); //
        assertEquals("black", config[2]); //
    }

    /**
     * Pruebas de unidad (GualdronL-VillagranR; G06).
     * Tema del documento (seccion 8): usa metodos que no estan en el enunciado.
     * Resultado reportado en el documento: No compila en nuestro proyecto: cannot find symbol -> method groupSetUp()
     * Adaptacion: se usa el atributo machine del setUp.
     */
    @Test
    public void accordingGlVrshouldGiveSymbols(){
        int NUMBER_WHEELS_TO_ADD = 5;
        for (int i = 0; i < NUMBER_WHEELS_TO_ADD; i++) {
            machine.addWheel(i); // Se agregan 5 ruedas
        }
        machine.addSymbol(0, "magenta");
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "yellow");
        machine.addSymbol(3, "blue");
        machine.addSymbol(4, "green");
        String[] proof = machine.symbols();
        String[] result = new String[]{"magenta", "red", "yellow", "blue", "green"};
        for (int i = 0; i < result.length;i++) {
            assertEquals(proof[i], result[i]); // Se verifica el resultado de symbols
        }
    }

    /**
     * Preparacion que la wiki asume y no incluye: tres ruedas, todas con
     * "red" al frente, y el catalogo con red, blue y green.
     */
    private void prepareThreeWheels(){
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(3, "green");
        for(int i = 1; i <= 3; i++){
            machine.addWheel(i);
            machine.placeSymbol(i, "red");
        }
    }
}