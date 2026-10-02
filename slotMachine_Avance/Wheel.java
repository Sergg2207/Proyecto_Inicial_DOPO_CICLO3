import java.util.ArrayList;

/**
 * Representa una rueda (reel) de la maquina tragamonedas. Guarda su
 * secuencia circular de simbolos (colores), cual queda al frente y si
 * esta fija. La rueda sabe girar sobre si misma (avanza sus simbolos
 * paso a paso) y sabe dibujarse: un marco que delimita donde empieza y
 * termina, y un circulo del color del simbolo al frente.
 *
 * @author Sergio Gonzalez, Juan Alvarez
 * @version 3.0
 */
public class Wheel{

    private static final int SPACING = 70;
    private static final int FRAME_SIZE = 60;
    private static final int BORDER = 4;
    private static final int STEP_DELAY = 300;

    private ArrayList<String> symbols;
    private int front;
    private boolean locked;
    private Rectangle frame;
    private Rectangle inside;
    private Circle circle;
    private int shownPosition;
    private boolean visible;
    private boolean highlighted;

    /**
     * Crea una rueda vacia, sin simbolos, sin fijar y sin dibujar.
     */
    public Wheel(){
        symbols = new ArrayList<String>();
        front = -1;
        locked = false;
        circle = null;
        shownPosition = -1;
        visible = false;
        highlighted = false;
    }

    /**
     * Coloca un simbolo al final de la secuencia de la rueda. Queda como
     * el simbolo al frente.
     * @param color color del simbolo a colocar.
     */
    public void place(String color){
        symbols.add(color);
        front = symbols.size() - 1;
    }

    /**
     * Deja al frente el simbolo del color indicado. Si la rueda ya lo
     * tiene, solo lo lleva al frente; si no, lo coloca.
     * @param color color que debe quedar al frente.
     */
    public void setFront(String color){
        int index = symbols.indexOf(color);
        if(index == -1){
            place(color);
        } else {
            front = index;
        }
    }

    /**
     * Retorna el color del simbolo que esta al frente de la rueda,
     * o null si la rueda no tiene simbolos.
     * @return color del simbolo al frente, o null si no hay simbolos.
     */
    public String getFrontColor(){
        if(front == -1){
            return null;
        }
        return symbols.get(front);
    }

    /**
     * Retorna cuantos simbolos tiene la rueda.
     * @return cantidad de simbolos de la rueda.
     */
    public int size(){
        return symbols.size();
    }

    /**
     * Fija la rueda: mientras este fija no debe girar.
     */
    public void lock(){
        locked = true;
    }

    /**
     * Suelta la rueda previamente fijada.
     */
    public void unlock(){
        locked = false;
    }

    /**
     * Indica si la rueda esta fija.
     * @return true si la rueda esta fija, false si no.
     */
    public boolean isLocked(){
        return locked;
    }

    /**
     * Indica si la rueda puede girar: no esta fija y tiene simbolos.
     * @return true si la rueda puede girar, false si no.
     */
    public boolean canSpin(){
        return !locked && !symbols.isEmpty();
    }

    /**
     * Gira la rueda el numero de pasos indicado. Cada paso avanza al
     * siguiente simbolo de la secuencia (al llegar al final vuelve al
     * primero). Si la rueda esta visible, cada paso se muestra en pantalla.
     * No hace nada si la rueda no puede girar.
     * @param steps cantidad de pasos a avanzar.
     */
    public void spin(int steps){
        if(!canSpin()){
            return;
        }
        for(int i = 0; i < steps; i++){
            front = (front + 1) % symbols.size();
            if(visible){
                paint();
                Canvas.getCanvas().wait(STEP_DELAY);
            }
        }
    }

    /**
     * Muestra esta rueda en el Canvas en la posicion indicada (1, 2, 3...
     * de izquierda a derecha). Las figuras se crean una sola vez y solo se
     * reposicionan cuando la posicion realmente cambia.
     * @param position posicion de esta rueda entre las demas (desde 1).
     */
    public void showAt(int position){
        if(circle == null){
            createShapes(position);
        } else if(position != shownPosition){
            moveTo(position);
        }
        visible = true;
        paint();
    }

    /**
     * Oculta la representacion visual de esta rueda.
     */
    public void hide(){
        visible = false;
        if(circle != null){
            hideShapes();
        }
    }

    /**
     * Resalta esta rueda (marco amarillo) para mostrar un estado ganador.
     */
    public void highlight(){
        setHighlighted(true);
    }

    /**
     * Quita el resaltado de esta rueda (marco negro).
     */
    public void unhighlight(){
        setHighlighted(false);
    }

    /**
     * Cambia el resaltado y, si la rueda esta visible, la vuelve a dibujar.
     * @param value true para resaltar la rueda, false para quitar el resaltado.
     */
    private void setHighlighted(boolean value){
        if(highlighted != value){
            highlighted = value;
            if(visible){
                paint();
            }
        }
    }

    /**
     * Crea el marco, el fondo y el circulo en la posicion indicada.
     * @param position posicion de la rueda entre las demas (desde 1).
     */
    private void createShapes(int position){
        frame = new Rectangle();
        frame.changeSize(FRAME_SIZE, FRAME_SIZE);
        frame.moveHorizontal(5 - 70 + (position - 1) * SPACING);
        frame.moveVertical(85 - 15);
        inside = new Rectangle();
        inside.changeSize(FRAME_SIZE - 2 * BORDER, FRAME_SIZE - 2 * BORDER);
        inside.moveHorizontal(5 + BORDER - 70 + (position - 1) * SPACING);
        inside.moveVertical(85 + BORDER - 15);
        circle = new Circle();
        circle.moveHorizontal((position - 1) * SPACING);
        circle.moveVertical(85);
        shownPosition = position;
    }

    /**
     * Mueve las tres figuras a la nueva posicion. Se ocultan antes para
     * que el cambio no se anime pixel por pixel.
     * @param position nueva posicion de la rueda (desde 1).
     */
    private void moveTo(int position){
        hideShapes();
        int distance = (position - shownPosition) * SPACING;
        frame.moveHorizontal(distance);
        inside.moveHorizontal(distance);
        circle.moveHorizontal(distance);
        shownPosition = position;
    }

    /**
     * Oculta las tres figuras de la rueda.
     */
    private void hideShapes(){
        frame.makeInvisible();
        inside.makeInvisible();
        circle.makeInvisible();
    }

    /**
     * Dibuja la rueda de atras hacia adelante: marco, fondo y circulo
     * con el color del simbolo al frente.
     */
    private void paint(){
        if(highlighted){
            frame.changeColor("yellow");
        } else {
            frame.changeColor("black");
        }
        frame.makeVisible();
        inside.changeColor("white");
        inside.makeVisible();
        String frontColor = getFrontColor();
        if(frontColor != null){
            circle.changeColor(frontColor);
        }
        circle.makeVisible();
    }
}