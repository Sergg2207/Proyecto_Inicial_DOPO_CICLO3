/**
 * Prueba de aceptacion del ciclo 1. Se ejecuta a mano en bluej: crear un
 * objeto de esta clase y llamar a run(). La maquina queda visible y
 * disponible en el object bench.
 *
 * @author Sergio Gonzalez Alba, Juan Andres Alvarez Silva
 * @version 2.0
 */
public class AcceptanceTestC1{

    /**
     * Crea una maquina con tres ruedas y cuatro simbolos, coloca los
     * simbolos en cada rueda, la hace visible, gira las ruedas y la deja
     * en estado ganador (todas las ruedas al frente con el mismo color).
     * @return la maquina en su estado final.
     */
    public SlotMachine run(){
        SlotMachine machine = createMachine();
        machine.makeVisible();
        machine.spin();
        leaveInJackpot(machine);
        return machine;
    }

    /**
     * Crea la maquina base: tres ruedas, cuatro simbolos y todos los
     * simbolos colocados en cada rueda.
     */
    private SlotMachine createMachine(){
        SlotMachine machine = new SlotMachine();
        for(int wheel = 1; wheel <= 3; wheel++){
            machine.addWheel(wheel);
        }
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

    /**
     * Deja el mismo simbolo al frente de todas las ruedas.
     * @param machine maquina a dejar en estado ganador.
     */
    private void leaveInJackpot(SlotMachine machine){
        for(int wheel = 1; wheel <= 3; wheel++){
            machine.placeSymbol(wheel, "red");
        }
    }
}