import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Pruebas unitarias de los metodos del ciclo 1 de SlotMachine. Cada
 * metodo se prueba con lo que debe hacer y con lo que no debe hacer.
 * Todas se ejecutan en modo invisible y cada caso prepara lo que
 * necesita (el setUp solo crea la maquina).
 *
 * @author Sergio Gonzalez Alba, Juan Andres Alvarez Silva
 * @version 1.0
 */
public class SlotMachineC1Test{

    private SlotMachine machine;

    @Before
    public void setUp(){
        machine = new SlotMachine();
    }

    /**
     * Una maquina nueva no tiene ruedas ni simbolos y su estado es ok.
     */
    @Test
    public void newMachineShouldStartEmptyAndOk(){
        assertTrue(machine.ok());
        assertEquals(0, machine.configuration().length);
        assertEquals(0, machine.symbols().length);
    }

    /**
     * addWheel agrega una rueda vacia.
     */
    @Test
    public void addWheelShouldAddAnEmptyWheel(){
        machine.addWheel(1);
        assertTrue(machine.ok());
        assertEquals(1, machine.configuration().length);
        assertNull(machine.configuration()[0]);
    }

    /**
     * addWheel ajusta posiciones fuera de rango.
     */
    @Test
    public void addWheelShouldClampPositionsOutOfRange(){
        machine.addWheel(1);
        machine.addWheel(0);
        machine.addWheel(100);
        assertTrue(machine.ok());
        assertEquals(3, machine.configuration().length);
    }

    /**
     * addWheel inserta la rueda en la posicion pedida y corre las demas.
     */
    @Test
    public void addWheelShouldInsertInTheGivenPosition(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.placeSymbol(1, "red");
        machine.addWheel(1);
        assertNull(machine.configuration()[0]);
        assertEquals("red", machine.configuration()[1]);
    }

    /**
     * delWheel elimina la rueda.
     */
    @Test
    public void delWheelShouldRemoveTheWheel(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.delWheel(1);
        assertTrue(machine.ok());
        assertEquals(1, machine.configuration().length);
    }

    /**
     * delWheel falla si no hay ruedas.
     */
    @Test
    public void delWheelShouldFailWhenThereAreNoWheels(){
        machine.delWheel(1);
        assertFalse(machine.ok());
    }

    /**
     * addSymbol respeta la posicion indicada.
     */
    @Test
    public void addSymbolShouldKeepTheGivenOrder(){
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(1, "green");
        assertArrayEquals(new String[]{"green", "red", "blue"}, machine.symbols());
    }

    /**
     * addSymbol ajusta posiciones fuera de rango.
     */
    @Test
    public void addSymbolShouldClampPositionsOutOfRange(){
        machine.addSymbol(1, "red");
        machine.addSymbol(100, "blue");
        machine.addSymbol(0, "green");
        assertArrayEquals(new String[]{"green", "red", "blue"}, machine.symbols());
    }

    /**
     * delSymbol elimina el simbolo del catalogo.
     */
    @Test
    public void delSymbolShouldRemoveTheSymbol(){
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.delSymbol("red");
        assertTrue(machine.ok());
        assertArrayEquals(new String[]{"blue"}, machine.symbols());
    }

    /**
     * delSymbol falla con un simbolo que no existe.
     */
    @Test
    public void delSymbolShouldFailWhenTheSymbolDoesNotExist(){
        machine.addSymbol(1, "red");
        machine.delSymbol("blue");
        assertFalse(machine.ok());
        assertEquals(1, machine.symbols().length);
    }

    /**
     * placeSymbol deja el simbolo al frente de la rueda.
     */
    @Test
    public void placeSymbolShouldLeaveTheSymbolAtTheFront(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(2, "blue");
        assertTrue(machine.ok());
        assertArrayEquals(new String[]{null, "blue"}, machine.configuration());
    }

    /**
     * placeSymbol falla con un simbolo fuera del catalogo.
     */
    @Test
    public void placeSymbolShouldFailWhenTheSymbolIsNotInTheCatalog(){
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.placeSymbol(1, "blue");
        assertFalse(machine.ok());
        assertNull(machine.configuration()[0]);
    }

    /**
     * placeSymbol falla si no hay ruedas.
     */
    @Test
    public void placeSymbolShouldFailWhenThereAreNoWheels(){
        machine.addSymbol(1, "red");
        machine.placeSymbol(1, "red");
        assertFalse(machine.ok());
    }

    /**
     * spin(wheel) deja al frente un simbolo de la rueda.
     */
    @Test
    public void spinShouldLeaveOneOfTheWheelSymbolsAtTheFront(){
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(1, "red");
        machine.placeSymbol(1, "blue");
        machine.spin(1);
        assertTrue(machine.ok());
        String front = machine.configuration()[0];
        assertTrue(front.equals("red") || front.equals("blue"));
    }

    /**
     * spin(wheel) falla si la rueda no tiene simbolos.
     */
    @Test
    public void spinShouldFailWhenTheWheelHasNoSymbols(){
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.spin(1);
        assertFalse(machine.ok());
    }

    /**
     * spin() deja cada rueda con uno de sus simbolos.
     */
    @Test
    public void spinAllShouldKeepAllWheelsWithinTheirSymbols(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");
        machine.spin();
        assertTrue(machine.ok());
        assertEquals("red", machine.configuration()[0]);
        assertEquals("blue", machine.configuration()[1]);
    }

    /**
     * spin() falla si no hay ruedas.
     */
    @Test
    public void spinAllShouldFailWhenThereAreNoWheels(){
        machine.addSymbol(1, "red");
        machine.spin();
        assertFalse(machine.ok());
    }

    /**
     * distinctSymbols no cuenta colores repetidos.
     */
    @Test
    public void distinctSymbolsShouldCountEachColorOnce(){
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(3, "red");
        assertEquals(2, machine.distinctSymbols());
    }

    /**
     * distinctSymbols es cero sin simbolos.
     */
    @Test
    public void distinctSymbolsShouldBeZeroWithoutSymbols(){
        assertEquals(0, machine.distinctSymbols());
    }

    /**
     * configuration retorna el color al frente de cada rueda, de izquierda a derecha.
     */
    @Test
    public void configurationShouldListTheFrontColorOfEachWheel(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(3, "green");
        machine.placeSymbol(1, "green");
        machine.placeSymbol(3, "red");
        assertArrayEquals(new String[]{"green", null, "red"}, machine.configuration());
    }

    /**
     * isJackpot es verdadero si todas las ruedas muestran el mismo color.
     */
    @Test
    public void isJackpotShouldBeTrueWhenAllWheelsShowTheSameColor(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
        machine.addSymbol(1, "red");
        machine.spin(new String[]{"red", "red", "red"});
        assertTrue(machine.isJackpot());
    }

    /**
     * isJackpot es falso si los colores difieren.
     */
    @Test
    public void isJackpotShouldBeFalseWhenColorsDiffer(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.spin(new String[]{"red", "blue"});
        assertFalse(machine.isJackpot());
    }

    /**
     * isJackpot es falso sin ruedas o con ruedas vacias.
     */
    @Test
    public void isJackpotShouldBeFalseWithoutWheelsOrSymbolsAtTheFront(){
        assertFalse(machine.isJackpot());
        machine.addWheel(1);
        assertFalse(machine.isJackpot());
    }

    /**
     * makeInvisible no altera el funcionamiento de la maquina.
     */
    @Test
    public void makeInvisibleShouldKeepTheMachineWorking(){
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.makeInvisible();
        machine.placeSymbol(1, "red");
        assertTrue(machine.ok());
        assertEquals("red", machine.configuration()[0]);
    }

    /**
     * exit deja la maquina sin ruedas ni simbolos.
     */
    @Test
    public void exitShouldLeaveTheMachineEmpty(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.exit();
        assertTrue(machine.ok());
        assertEquals(0, machine.configuration().length);
        assertEquals(0, machine.symbols().length);
    }
}
