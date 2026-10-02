/**
 * Pruebas de aceptacion del ciclo 2. Se ejecutan a mano en BlueJ: crear un
 * objeto de esta clase y llamar a lockAndSpinSteps() o swapAndSetJackpot().
 * Cada metodo deja la maquina visible en el object bench.
 *
 * @author Sergio Gonzalez Alba, Juan Andres Alvarez Silva
 * @version 1.0
 */
public class AcceptanceTestC2{

    /**
     * Crea una maquina de tres ruedas con simbolos, fija la rueda 2 y la
     * gira: no debe moverse. Luego la suelta y gira la rueda 1 varios pasos
     * (se ve paso a paso).
     * @return la maquina en su estado final.
     */
    public SlotMachine lockAndSpinSteps(){
        SlotMachine machine = createMachine();
        machine.makeVisible();
        machine.lock(2);
        machine.spin(2, 3);
        machine.unlock(2);
        machine.spin(1, 4);
        return machine;
    }

    /**
     * Crea una maquina de tres ruedas con simbolos, intercambia las ruedas
     * 1 y 3, y deja la maquina en una configuracion ganadora con
     * spin(setSymbols).
     * @return la maquina en su estado final.
     */
    public SlotMachine swapAndSetJackpot(){
        SlotMachine machine = createMachine();
        machine.makeVisible();
        machine.swap(1, 3);
        machine.spin(new String[]{"blue", "blue", "blue"});
        return machine;
    }

    /**
     * Crea la maquina base: tres ruedas, cuatro simbolos y todos los
     * simbolos colocados en cada rueda.
     */
    private SlotMachine createMachine(){
        SlotMachine machine = new SlotMachine();
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(3, "green");
        machine.addSymbol(4, "yellow");
        for(int wheel = 1; wheel <= 3; wheel++){
            machine.placeSymbol(wheel, "red");
            machine.placeSymbol(wheel, "blue");
            machine.placeSymbol(wheel, "green");
            machine.placeSymbol(wheel, "yellow");
        }
        return machine;
    }
}
