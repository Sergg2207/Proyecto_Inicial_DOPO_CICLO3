import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Pruebas unitarias de los metodos del ciclo 2 de SlotMachine: swap, lock,
 * unlock, spin(wheel,steps) y spin(setSymbols), y del efecto del ciclo 2
 * sobre spin() y spin(wheel). Todas se ejecutan en modo invisible y cada
 * caso prepara lo que necesita (el setUp solo crea la maquina).
 *
 * @author Sergio Gonzalez Alba, Juan Andres Alvarez Silva
 * @version 3.0
 */
public class SlotMachineC2Test{

    private SlotMachine machine;

    @Before
    public void setUp(){
        machine = new SlotMachine();
    }

    /**
     * swap intercambia los simbolos al frente de dos ruedas.
     */
    @Test
    public void swapShouldExchangeTheFrontSymbolsOfTwoWheels(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");
        machine.swap(1, 2);
        assertTrue(machine.ok());
        assertArrayEquals(new String[]{"blue", "red"}, machine.configuration());
    }

    /**
     * El estado fijo viaja con la rueda al intercambiar.
     */
    @Test
    public void swapShouldMoveTheLockedStateAlongWithTheWheel(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");
        machine.lock(1);
        machine.swap(1, 2);
        machine.spin(2, 3);
        assertFalse("La rueda fija ahora esta en la posicion 2", machine.ok());
        machine.spin(1, 3);
        assertTrue("La rueda suelta ahora esta en la posicion 1", machine.ok());
    }

    /**
     * swap falla con menos de dos ruedas.
     */
    @Test
    public void swapShouldFailWhenThereAreLessThanTwoWheels(){
        machine.addWheel(1);
        machine.swap(1, 1);
        assertFalse(machine.ok());
    }

    /**
     * swap ajusta posiciones fuera de rango.
     */
    @Test
    public void swapShouldClampPositionsOutOfRange(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
        machine.swap(0, 100);
        assertTrue(machine.ok());
    }

    /**
     * Una rueda fija no gira.
     */
    @Test
    public void lockShouldPreventTheWheelFromSpinning(){
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(1, "red");
        machine.lock(1);
        machine.spin(1);
        assertFalse(machine.ok());
        assertEquals("red", machine.configuration()[0]);
    }

    /**
     * lock falla si no hay ruedas.
     */
    @Test
    public void lockShouldFailWhenThereAreNoWheels(){
        machine.lock(1);
        assertFalse(machine.ok());
    }

    /**
     * Al soltar una rueda vuelve a poder girar.
     */
    @Test
    public void unlockShouldAllowTheWheelToSpinAgain(){
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(1, "red");
        machine.lock(1);
        machine.unlock(1);
        machine.spin(1);
        assertTrue(machine.ok());
    }

    /**
     * lock ajusta la posicion 100 a la ultima rueda.
     */
    @Test
    public void lockShouldClampPositionsOutOfRange(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
        machine.addSymbol(1, "red");
        machine.placeSymbol(3, "red");
        machine.lock(100);
        machine.spin(3);
        assertFalse(machine.ok());
    }

    /**
     * Cada paso avanza al siguiente simbolo de la rueda y da la vuelta al llegar al final.
     */
    @Test
    public void spinWithStepsShouldAdvanceToTheNextSymbolsInOrder(){
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(3, "green");
        machine.placeSymbol(1, "red");
        machine.placeSymbol(1, "blue");
        machine.placeSymbol(1, "green");
        machine.spin(1, 1);
        assertEquals("red", machine.configuration()[0]);
        machine.spin(1, 2);
        assertEquals("green", machine.configuration()[0]);
    }

    /**
     * Una vuelta completa deja el mismo simbolo al frente.
     */
    @Test
    public void spinWithStepsShouldLeaveTheSameSymbolAfterAFullTurn(){
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(3, "green");
        machine.placeSymbol(1, "red");
        machine.placeSymbol(1, "blue");
        machine.placeSymbol(1, "green");
        machine.spin(1, 3);
        assertTrue(machine.ok());
        assertEquals("green", machine.configuration()[0]);
    }

    /**
     * spin con pasos no mueve una rueda fija.
     */
    @Test
    public void spinWithStepsShouldFailWhenTheWheelIsLocked(){
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(1, "red");
        machine.lock(1);
        machine.spin(1, 5);
        assertFalse(machine.ok());
        assertEquals("red", machine.configuration()[0]);
    }

    /**
     * spin con pasos falla si los pasos no son positivos.
     */
    @Test
    public void spinWithStepsShouldFailWhenStepsIsNotPositive(){
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.placeSymbol(1, "red");
        machine.spin(1, 0);
        assertFalse(machine.ok());
        machine.spin(1, -2);
        assertFalse(machine.ok());
    }

    /**
     * spin con pasos falla si la rueda no tiene simbolos.
     */
    @Test
    public void spinWithStepsShouldFailWhenTheWheelHasNoSymbols(){
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.spin(1, 3);
        assertFalse(machine.ok());
        assertNull(machine.configuration()[0]);
    }

    /**
     * spin(setSymbols) deja los colores dados al frente.
     */
    @Test
    public void spinWithConfigurationShouldSetTheGivenColors(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(3, "green");
        machine.spin(new String[]{"red", "blue", "green"});
        assertTrue(machine.ok());
        assertArrayEquals(new String[]{"red", "blue", "green"}, machine.configuration());
    }

    /**
     * Si la rueda ya tiene el color, solo lo lleva al frente sin duplicarlo.
     */
    @Test
    public void spinWithConfigurationShouldReuseASymbolTheWheelAlreadyHas(){
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(1, "red");
        machine.placeSymbol(1, "blue");
        machine.spin(new String[]{"red"});
        assertEquals("red", machine.configuration()[0]);
        machine.spin(1, 1);
        assertEquals("blue", machine.configuration()[0]);
    }

    /**
     * spin(setSymbols) no modifica ruedas fijas.
     */
    @Test
    public void spinWithConfigurationShouldIgnoreLockedWheels(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(3, "green");
        machine.placeSymbol(2, "green");
        machine.lock(2);
        machine.spin(new String[]{"red", "blue", "red"});
        assertTrue(machine.ok());
        assertEquals("green", machine.configuration()[1]);
    }

    /**
     * spin(setSymbols) falla con un color fuera del catalogo, sin afectar los demas.
     */
    @Test
    public void spinWithConfigurationShouldFailWithAColorOutsideTheCatalog(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(3, "green");
        machine.spin(new String[]{"red", "purple", "green"});
        assertFalse(machine.ok());
        assertEquals("red", machine.configuration()[0]);
        assertNull(machine.configuration()[1]);
        assertEquals("green", machine.configuration()[2]);
    }

    /**
     * Las posiciones sobrantes se ignoran.
     */
    @Test
    public void spinWithConfigurationShouldIgnorePositionsBeyondTheWheelCount(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(3, "green");
        machine.spin(new String[]{"red", "blue", "green", "red", "blue"});
        assertTrue(machine.ok());
        assertEquals(3, machine.configuration().length);
    }

    /**
     * spin(setSymbols) falla con arreglo nulo.
     */
    @Test
    public void spinWithConfigurationShouldFailWhenTheArrayIsNull(){
        machine.addWheel(1);
        machine.spin((String[]) null);
        assertFalse(machine.ok());
    }

    /**
     * spin() no cambia las ruedas fijas.
     */
    @Test
    public void spinAllShouldSkipLockedWheels(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(2, "red");
        machine.placeSymbol(2, "blue");
        machine.lock(2);
        machine.spin();
        assertTrue(machine.ok());
        assertEquals("blue", machine.configuration()[1]);
    }

    /**
     * spin() salta las ruedas sin simbolos sin fallar.
     */
    @Test
    public void spinAllShouldSkipWheelsWithoutSymbols(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(1, "red");
        machine.placeSymbol(1, "blue");
        machine.spin();
        assertTrue(machine.ok());
        assertNull(machine.configuration()[1]);
    }
}
