    // build a brick with and id and position (on the matrix) out of the 2 parts of the brick
    public class Brick {
        private int id;
        private Position positionA;
        private Position positionB;


     //set id and position of the first half of the brick only
    public Brick(int id, Position position) {
        this.id = id;
        this.positionA = position;
        this.positionB = null;
    }

    public int getId() {
        return id;
    }

    public Position getPositionA() {
        return positionA;
    }

    public Position getPositionB() {
        return positionB;
    }


    //set position of end half of the brick if is correct position by the position of first half
    public void setPositionB(Position position) {
        int coefficient = Math.abs(this.positionA.getRow() - position.getRow()) +
                Math.abs(this.positionA.getCol() - position.getCol());
        if (coefficient == 1) {
            this.positionB = position;
        }
    }
}