import static org.junit.Assert.*;
import java.util.Arrays;
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

    // ---------------------------------------------------------------
    // PRUEBAS WIKI
    // ---------------------------------------------------------------

    // Las siguientes son las pruebas de la wiki que aparecen en el documento
    // "Errores por revisar_WIKI" (las que fallaban o no compilaban).

    /**
     * Grupo CarreroGomez.
     * Tema del documento (seccion 1): addSymbol y que exige placeSymbol.
     * Resultado reportado en el documento: Falla en nuestro proyecto: expected red but was null
     */
    @Test
    public void accordingCcGbShouldNotSpinLockedWheel(){
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.lock(1);
        machine.spin(1);
        assertEquals("red", machine.configuration()[0]);
        assertFalse(machine.ok());
    }

    /**
     * Grupo CarreroGomez.
     * Tema del documento (seccion 1): addSymbol y que exige placeSymbol.
     * Resultado reportado en el documento: Falla en nuestro proyecto: expected blue but was null
     */
    @Test
    public void accordingCcGbShouldSpinAfterUnlock(){
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.lock(1);
        machine.unlock(1);
        machine.spin(1);
        assertEquals("blue", machine.configuration()[0]);
        assertTrue(machine.ok());
    }

    /**
     * Grupo CañonA - PaezP.
     * Tema del documento (seccion 1): addSymbol y que exige placeSymbol.
     * Resultado reportado en el documento: Falla en nuestro proyecto: expected red but was green
     */
    @Test
    public void accordingCaPpShouldSpinByStepsAndDetectJackpotCorrectly(){
        SlotMachine slotMachine = new SlotMachine();
        slotMachine.makeInvisible();
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(1, "blue");
        slotMachine.addSymbol(2, "red");
        slotMachine.addSymbol(2, "green");
        slotMachine.spin(new String[]{"red", "green"});
        assertTrue(slotMachine.ok());
        slotMachine.spin(2, 1);
        assertTrue(slotMachine.ok());
        String[] currentConfiguration = slotMachine.configuration();
        assertEquals("red", currentConfiguration[0]);
        assertEquals("red", currentConfiguration[1]);
        assertTrue(slotMachine.isJackpot());
    }

    /**
     * Grupo Tatiana Lopez - Juan Uribe.
     * Tema del documento (seccion 1): addSymbol y que exige placeSymbol.
     * Resultado reportado en el documento: Falla en nuestro proyecto: expected true
     */
    @Test
    public void unlockShouldAllowSpinAgain(){
        SlotMachine m = new SlotMachine();
        m.addSymbol(1, "red");
        m.addSymbol(2, "blue");
        m.addWheel(1);
        m.lock(1);
        m.unlock(1);
        m.spin(1);
        assertTrue(m.ok());
    }

    /**
     * Grupo Tatiana Lopez - Juan Uribe.
     * Tema del documento (seccion 1): addSymbol y que exige placeSymbol.
     * Resultado reportado en el documento: Falla en nuestro proyecto: expected red but was green
     */
    @Test
    public void spinStepsShouldMoveForwardCyclically(){
        SlotMachine m = new SlotMachine();
        m.addSymbol(1, "red");
        m.addSymbol(2, "blue");
        m.addSymbol(3, "green");
        m.addWheel(1);
        m.placeSymbol(1, "green");
        m.spin(1, 1);
        String[] config = m.configuration();
        assertEquals("red", config[0]);
    }

    /**
     * Grupo 06 CuervoC -- InfanteC.
     * Tema del documento (seccion 1): addSymbol y que exige placeSymbol.
     * Resultado reportado en el documento: Falla en nuestro proyecto: expected [blue, red] but was [null, null]
     * Adaptacion: se usa el atributo machine del setUp.
     */
    @Test
    public void accordingCcIcshouldSwapUnlockedWheels(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");
        machine.swap(1, 2);
        assertTrue(machine.ok());
        assertArrayEquals(new String[]{"blue", "red"}, machine.configuration());
    }

    /**
     * Grupo 06 CuervoC -- InfanteC.
     * Tema del documento (seccion 1): addSymbol y que exige placeSymbol.
     * Resultado reportado en el documento: Falla en nuestro proyecto: expected not null
     * Adaptacion: se usa el atributo machine del setUp.
     */
    @Test
    public void accordingCcIcshouldSpinWheelTheRequestedNumberOfSteps(){
        machine.addWheel(1);
        machine.placeSymbol(1, "red");
        machine.spin(1, 3);
        assertNotNull(machine.configuration()[0]);
        assertTrue(machine.ok());
    }

    /**
     * Grupo 06 CuervoC -- InfanteC.
     * Tema del documento (seccion 1): addSymbol y que exige placeSymbol.
     * Resultado reportado en el documento: Falla en nuestro proyecto: expected red but was null
     * Adaptacion: se usa el atributo machine del setUp.
     */
    @Test
    public void accordingCcIcshouldKeepWheelSymbolWhenSpinHasZeroSteps(){
        machine.addWheel(1);
        machine.placeSymbol(1, "red");
        machine.spin(1, 0);
        assertEquals("red", machine.configuration()[0]);
        assertTrue(machine.ok());
    }

    /**
     * Grupo 06 CuervoC -- InfanteC.
     * Tema del documento (seccion 1): addSymbol y que exige placeSymbol.
     * Resultado reportado en el documento: Falla en nuestro proyecto: expected red but was null
     * Adaptacion: se usa el atributo machine del setUp.
     */
    @Test
    public void accordingCcIcshouldNotSpinWheelWithNegativeSteps(){
        machine.addWheel(1);
        machine.placeSymbol(1, "red");
        machine.spin(1, -1);
        assertEquals("red", machine.configuration()[0]);
        assertFalse(machine.ok());
    }

    /**
     * Grupo AnzolaA-CastañedaB.
     * Tema del documento (seccion 1): addSymbol y que exige placeSymbol.
     * Resultado reportado en el documento: Falla en nuestro proyecto: expected blue but was null
     */
    @Test
    public void accordingAaCbShouldExchangeTwoWheels(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");
        machine.swap(1, 2);
        assertEquals("blue", machine.configuration()[0]);
        assertEquals("red", machine.configuration()[1]);
    }

    /**
     * Grupo Ibarra y Poveda.
     * Tema del documento (seccion 2): swap con una rueda fija.
     * Resultado reportado en el documento: Falla en nuestro proyecto: expected false
     * Adaptacion: se usa el atributo machine del setUp.
     */
    @Test
    public void accordingIcPgShouldNotSwap(){
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
        machine.lock(1);
        machine.swap(1, 3);
        assertFalse(machine.ok());
    }

    /**
     * Grupo Ibarra y Poveda.
     * Tema del documento (seccion 2): swap con una rueda fija.
     * Resultado reportado en el documento: Falla en nuestro proyecto: expected false
     * Adaptacion: se usa el atributo machine del setUp.
     */
    @Test
    public void shouldNotSwap(){
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
        machine.lock(1);
        machine.swap(1, 3);
        assertFalse(machine.ok());
    }

    /**
     * Grupo CañonA - PaezP.
     * Tema del documento (seccion 2): swap con una rueda fija.
     * Resultado reportado en el documento: Falla en nuestro proyecto: expected false
     * Adaptacion: el documento muestra esta prueba cortada con "..."; se incluye hasta donde aparece.
     */
    @Test
    public void accordingCaPpShouldNotSwapWhenWheelIsLockedAndWorkWhenUnlocked(){
        SlotMachine slotMachine = new SlotMachine();
        slotMachine.makeInvisible();
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
        slotMachine.addWheel(3);
        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(1, "blue");
        slotMachine.addSymbol(2, "green");
        slotMachine.addSymbol(2, "yellow");
        slotMachine.addSymbol(3, "magenta");
        slotMachine.addSymbol(3, "black");
        slotMachine.spin(new String[]{"blue", "yellow", "magenta"});
        assertEquals("blue", slotMachine.configuration()[0]);
        slotMachine.lock(2);
        slotMachine.swap(1, 2);
        assertFalse(slotMachine.ok());
        assertEquals("yellow", slotMachine.configuration()[1]);
        slotMachine.unlock(2);
        slotMachine.swap(1, 2);
        assertTrue(slotMachine.ok());
        assertEquals("yellow", slotMachine.configuration()[0]);
    }

    /**
     * Grupo 06 CuervoC -- InfanteC.
     * Tema del documento (seccion 2): swap con una rueda fija.
     * Resultado reportado en el documento: Falla en nuestro proyecto: expected false
     * Adaptacion: se usa el atributo machine del setUp.
     */
    @Test
    public void accordingCcIcshouldNotSwapLockedWheels(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.lock(1);
        machine.swap(1, 2);
        assertFalse(machine.ok());
    }

    /**
     * Grupo GonzalezM-LesmesA.
     * Tema del documento (seccion 3): reglas de spin(String[] setSymbols).
     * Resultado reportado en el documento: Falla en nuestro proyecto: dejar la máquina en una configuración dada debe ser exitoso
     */
    @Test
    public void accordingGmLaShouldReflectExactConfigurationAfterSpinWithGivenSymbols(){
        SlotMachine machine = new SlotMachine();
        machine.spin(new String[] { "yellow", "red", "blue" });
        assertTrue("dejar la máquina en una configuración dada debe ser exitoso", machine.ok());
        assertArrayEquals(new String[] { "yellow", "red", "blue" }, machine.configuration());
    }

    /**
     * Grupo MeloR-SanabriaE.
     * Tema del documento (seccion 3): reglas de spin(String[] setSymbols).
     * Resultado reportado en el documento: Falla en nuestro proyecto: expected [null, null] but was [red, null]
     */
    @Test
    public void accordingMrSeShouldRejectSpinSetSymbolsWhenColorMissing(){
        SlotMachine machine = new SlotMachine();
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "green");
        String[] before = machine.configuration();
        machine.spin(new String[]{"red", "purple"}); // "purple" no existe
        assertFalse(machine.ok());
        assertArrayEquals(before, machine.configuration()); // nada se movió
    }

    /**
     * Grupo Tatiana Lopez - Juan Uribe.
     * Tema del documento (seccion 3): reglas de spin(String[] setSymbols).
     * Resultado reportado en el documento: Falla en nuestro proyecto: expected false
     */
    @Test
    public void spinSetSymbolsShouldFailWithWrongSize(){
        SlotMachine m = new SlotMachine();
        m.addSymbol(1, "red");
        m.addWheel(1);
        m.addWheel(2);
        String[] setSymbols = {"red"};
        m.spin(setSymbols);
        assertFalse(m.ok());
    }

    /**
     * Grupo CardozoDiaz.
     * Tema del documento (seccion 3): reglas de spin(String[] setSymbols).
     * Resultado reportado en el documento: Falla en nuestro proyecto: expected false
     */
    @Test
    public void accordingCbDbShouldNotSpinWhenAtLeastOneWheelIsLocked(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
        machine.addSymbol(1,"red");
        machine.addSymbol(2,"yellow");
        machine.addSymbol(3,"blue");
        machine.lock(1);
        machine.spin(new String[]{"yellow","blue","red"});
        assertFalse(machine.ok());
    }

    /**
     * Grupo GarcíaJ-QuezadaK.
     * Tema del documento (seccion 6): reglas que no estan en el enunciado.
     * Resultado reportado en el documento: Falla en nuestro proyecto: expected true
     */
    @Test
    public void accordingGjQkShouldSucceedWithoutChangingConfigurationWhenSpinningZeroSteps(){
        SlotMachine m = new SlotMachine();
        m.addWheel(1);
        m.addSymbol(1, "red");
        m.addSymbol(2, "blue");
        String before = m.configuration()[0];
        m.spin(1, 0);
        assertTrue(m.ok());
        assertEquals(before, m.configuration()[0]);
    }

    /**
     * Grupo Murillo.
     * Tema del documento (seccion 7): prueba sin setUp en la wiki.
     * Resultado reportado en el documento: Falla en nuestro proyecto: expected true
     * Adaptacion: la wiki no trae el setUp: se agrega prepareThreeWheels().
     */
    @Test
    public void accordingMurShouldSwapLockedWheelsAndMaintainLockState(){
        prepareThreeWheels();
        machine.lock(1);
        assertTrue(machine.ok());
        machine.swap(1, 2);
        assertTrue(machine.ok());
        machine.spin(2, 3);
        assertFalse(machine.ok());
        machine.spin(1, 3);
        assertTrue(machine.ok());
    }

    /**
     * Grupo Murillo.
     * Tema del documento (seccion 7): prueba sin setUp en la wiki.
     * Resultado reportado en el documento: Falla en nuestro proyecto: expected true
     * Adaptacion: la wiki no trae el setUp: se agrega prepareThreeWheels().
     */
    @Test
    public void accordingMurShouldSpinUnlockedWheelsOnlyWhenArrayIsApplied(){
        prepareThreeWheels();
        String[] symbolstoput = {"red", "red", "red"};
        machine.spin(symbolstoput);
        assertTrue(machine.ok());
        machine.lock(2);
        assertTrue(machine.ok());
        String[] newConfig = {"blue", "blue", "blue"};
        machine.spin(newConfig);
        assertTrue(machine.ok());
        assertFalse(machine.isJackpot());
    }

    /**
     * Grupo CarreñoG-CastañoP.
     * Tema del documento (seccion 7): prueba sin setUp en la wiki.
     * Resultado reportado en el documento: Falla en nuestro proyecto: expected false
     * Adaptacion: la wiki no trae el setUp: se agrega prepareThreeWheels(); se agrega un segundo simbolo a la rueda 1 para que el giro pueda cambiar la configuracion.
     */
    @Test
    public void shouldChangeConfigurationWhenWheelIsUnlockedAfterLock(){
        prepareThreeWheels();
        machine.placeSymbol(1, "blue");
        machine.lock(1);
        machine.unlock(1);
        String[] before = machine.configuration();
        machine.spin(1, 1);
        String[] after = machine.configuration();
        assertFalse(Arrays.equals(before, after));
    }

    /**
     * Grupo FrancoS - GarciaC.
     * Tema del documento (seccion 7): prueba sin setUp en la wiki.
     * Resultado reportado en el documento: Falla en nuestro proyecto: expected true
     * Adaptacion: se usa el atributo machine del setUp; la wiki no trae el setUp: se agrega prepareThreeWheels().
     */
    @Test
    public void fsGcShouldKeepDistinctSymbolCountAfterSwap(){
        prepareThreeWheels();
        int before = machine.distinctSymbols();
        machine.swap(1, 2);
        assertTrue(machine.ok());
        assertEquals(before, machine.distinctSymbols());
    }

    /**
     * Grupo 07 - Juan C-Santiago R.
     * Tema del documento (seccion 7): prueba sin setUp en la wiki.
     * Resultado reportado en el documento: Falla nuestro proyecto: java.lang.ArrayIndexOutOfBoundsException: Index 0 out of bounds for length 0
     * Adaptacion: la wiki no trae el setUp: se agrega prepareThreeWheels().
     */
    @Test
    public void swapShouldExchangeWheels(){
        prepareThreeWheels();
        String[] before = machine.configuration();
        machine.swap(1, 3);
        String[] after = machine.configuration();
        assertEquals(before[0], after[2]);
        assertEquals(before[2], after[0]);
    }

    /**
     * Grupo Gonzalez-Lopez.
     * Tema del documento (seccion 7): prueba sin setUp en la wiki.
     * Resultado reportado en el documento: Falla en nuestro proyecto: expected true
     * Adaptacion: se usa el atributo machine del setUp; la wiki no trae el setUp: se agrega prepareThreeWheels().
     */
    @Test
    public void accordingGLShouldSwapTwoWheelPositions(){
        prepareThreeWheels();
        machine.placeSymbol(1, "blue");
        machine.placeSymbol(3, "green");
        machine.swap(1, 3);
        assertTrue(machine.ok());
        assertArrayEquals(new String[]{"green", "red", "blue"}, machine.configuration());
    }

    /**
     * Grupo Gonzalez-Lopez.
     * Tema del documento (seccion 7): prueba sin setUp en la wiki.
     * Resultado reportado en el documento: Falla en nuestro proyecto: expected true
     * Adaptacion: se usa el atributo machine del setUp; la wiki no trae el setUp: se agrega prepareThreeWheels().
     */
    @Test
    public void accordingGLShouldKeepLockedWheelUnchangedOnGlobalSpin(){
        prepareThreeWheels();
        machine.lock(2);
        machine.spin();
        assertTrue(machine.ok());
        assertEquals("red", machine.configuration()[1]);
    }

    /**
     * Grupo Lizarazo - Bohorquez.
     * Tema del documento (seccion 7): prueba sin setUp en la wiki.
     * Resultado reportado en el documento: Falla en nuestro proyecto: expected true
     * Adaptacion: la wiki no trae el setUp: se agrega prepareThreeWheels().
     */
    @Test
    public void shouldLockWheelAndPreventSpinOnIt(){
        prepareThreeWheels();
        machine.lock(0);
        assertTrue(machine.ok());
        machine.spin(0);
        assertFalse(machine.ok());
        assertEquals("red", machine.configuration()[0]);
    }

    /**
     * Grupo DiazR- RojasM.
     * Tema del documento (seccion 8): usa metodos que no estan en el enunciado.
     * Resultado reportado en el documento: No compila en nuestro proyecto: method addSymbol in class SlotMachine cannot be applied to given types;
     * Adaptacion: se usa el atributo machine del setUp; addSymbol(pos, color, forma) -> addSymbol(pos, color), la forma no esta en el enunciado.
     */
    @Test
    public void accordingDrRmShouldBeJackpotWhenAllWheelsMatch(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.spin(new String[]{"red", "red", "red"});
        assertTrue(machine.isJackpot());
    }

    /**
     * Grupo DiazR- RojasM.
     * Tema del documento (seccion 8): usa metodos que no estan en el enunciado.
     * Resultado reportado en el documento: No compila en nuestro proyecto: method addSymbol in class SlotMachine cannot be applied to given types;
     * Adaptacion: se usa el atributo machine del setUp; addSymbol(pos, color, forma) -> addSymbol(pos, color), la forma no esta en el enunciado.
     */
    @Test
    public void accordingDrRmShouldKeepCorrectWheelCountAfterAddAndDelete(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
        machine.delWheel(2);
        machine.addSymbol(1, "red");
        machine.spin(new String[]{"red", "red"});
        assertEquals(2, machine.configuration().length);
    }

    /**
     * Grupo BarraganA-GuerreroB.
     * Tema del documento (seccion 8): usa metodos que no estan en el enunciado.
     * Resultado reportado en el documento: No compila en nuestro proyecto: cannot find symbol -> method spinStep(int,int)
     * Adaptacion: spinStep -> spin(wheel, steps).
     */
    @Test
    public void accordingBaGqShouldNotSpinLockedWheel(){
        SlotMachine maq = new SlotMachine();
        maq.addWheel(1);
        maq.addSymbol(1, "red");
        maq.addSymbol(2, "blue");
        maq.placeSymbol(1, "red");
        maq.lock(1);
        maq.spin(1, 1);
        assertFalse(maq.ok());
        String[] config = maq.configuration();
        assertEquals("red", config[0]);
    }

    /**
     * Grupo BustosL-GomezG.
     * Tema del documento (seccion 8): usa metodos que no estan en el enunciado.
     * Resultado reportado en el documento: No compila en nuestro proyecto: cannot find symbol -> method setConfiguration(String[])
     * Adaptacion: setConfiguration -> spin(String[] setSymbols).
     */
    @Test
    public void accordingIbPoShouldSetValidConfiguration(){
        SlotMachine slotMachine = new SlotMachine();
        slotMachine.addWheel(0);
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
        slotMachine.addSymbol(0, "red");
        slotMachine.addSymbol(0, "blue");
        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(1, "blue");
        slotMachine.addSymbol(2, "red");
        slotMachine.addSymbol(2, "blue");
        slotMachine.spin(
        new String[]{"blue", "red", "blue"}
        );
        String[] configuration = slotMachine.configuration();
        assertEquals("blue", configuration[0]);
        assertEquals("red", configuration[1]);
        assertEquals("blue", configuration[2]);
    }

    /**
     * Grupo BustosL-GomezG.
     * Tema del documento (seccion 8): usa metodos que no estan en el enunciado.
     * Resultado reportado en el documento: No compila en nuestro proyecto: cannot find symbol -> method setConfiguration(String[])
     * Adaptacion: setConfiguration -> spin(String[] setSymbols).
     */
    @Test
    public void accordingIbPoShouldNotChangeConfigurationWithWrongSize(){
        SlotMachine slotMachine = new SlotMachine();
        slotMachine.addWheel(0);
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
        slotMachine.addSymbol(0, "red");
        slotMachine.addSymbol(0, "blue");
        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(1, "blue");
        slotMachine.addSymbol(2, "red");
        slotMachine.addSymbol(2, "blue");
        slotMachine.spin(
        new String[]{"red", "red", "red"}
        );
        String[] before = slotMachine.configuration();
        slotMachine.spin(
        new String[]{"blue", "blue"}
        );
        String[] after = slotMachine.configuration();
        assertArrayEquals(before, after);
    }

    /**
     * Grupo REYESL - BARRAGANB.
     * Tema del documento (seccion 8): usa metodos que no estan en el enunciado.
     * Resultado reportado en el documento: No compila en nuestro proyecto: incompatible types: String[] cannot be converted to String
     * Adaptacion: configuration() retorna String[]: se une con espacios para comparar.
     */
    @Test
    public void accordingBBRLShouldSwapWheels(){
        SlotMachine slotMachine = new SlotMachine();
        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(2, "blue");
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
        slotMachine.placeSymbol(1, "red");
        slotMachine.placeSymbol(2, "blue");
        String before = String.join(" ", slotMachine.configuration());
        slotMachine.swap(1, 2);
        String after = String.join(" ", slotMachine.configuration());
        assertTrue(before.equals("red blue"));
        assertTrue(after.equals("blue red"));
    }

    /**
     * Grupo REYESL - BARRAGANB.
     * Tema del documento (seccion 8): usa metodos que no estan en el enunciado.
     * Resultado reportado en el documento: No compila en nuestro proyecto: incompatible types: String[] cannot be converted to String
     * Adaptacion: configuration() retorna String[]: se une con espacios para comparar.
     */
    @Test
    public void accordingBBRLShouldNotSpinWhenLocked(){
        SlotMachine slotMachine = new SlotMachine();
        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(2, "blue");
        slotMachine.addSymbol(3, "green");
        slotMachine.addWheel(1);
        slotMachine.placeSymbol(1, "red");
        slotMachine.lock(1);
        String before = String.join(" ", slotMachine.configuration());
        slotMachine.spin(1, 1);
        String after = String.join(" ", slotMachine.configuration());
        assertTrue(before.equals("red"));
        assertTrue(after.equals("red"));
    }

    /**
     * Grupo 06 CuervoC -- InfanteC.
     * Tema del documento (seccion 8): usa metodos que no estan en el enunciado.
     * Resultado reportado en el documento: No compila en nuestro proyecto: no suitable method found for spin(String)
     * Adaptacion: se usa el atributo machine del setUp; spin("a, b") -> spin(String[]) separando por ", ".
     */
    @Test
    public void accordingCcIcshouldSetConfigurationWithSpinString(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.spin("red, blue".split(", "));
        assertArrayEquals(new String[]{"red", "blue"},
        machine.configuration());
        assertTrue(machine.ok());
    }

    /**
     * Grupo 06 CuervoC -- InfanteC.
     * Tema del documento (seccion 8): usa metodos que no estan en el enunciado.
     * Resultado reportado en el documento: No compila en nuestro proyecto: no suitable method found for spin(String)
     * Adaptacion: se usa el atributo machine del setUp; spin("a, b") -> spin(String[]) separando por ", ".
     */
    @Test
    public void accordingCcIcshouldNotSetConfigurationWithUnsupportedSymbol(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");
        machine.spin("red, purple".split(", "));
        assertArrayEquals(new String[]{"red", "blue"},
        machine.configuration());
        assertFalse(machine.ok());
    }

    /**
     * Grupo 06 CuervoC -- InfanteC.
     * Tema del documento (seccion 8): usa metodos que no estan en el enunciado.
     * Resultado reportado en el documento: No compila en nuestro proyecto: no suitable method found for spin(String)
     * Adaptacion: se usa el atributo machine del setUp; spin("a, b") -> spin(String[]) separando por ", ".
     */
    @Test
    public void accordingCcIcshouldNotSetConfigurationWithWrongNumberOfSymbols(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.spin("red".split(", "));
        assertFalse(machine.ok());
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