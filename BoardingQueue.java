
public class BoardingQueue {
	// implemented with array
	// how do I make this a proprity Q?

	// data fields
	private final int CAPACITY = 10; // max 10 passengers can wait before boarding flight
	private int front;
	private int rear;
	private int size;
	private int cursor;
	private Passenger[] data; // passengers
	private int count;

	// Constructors
	public BoardingQueue() {
		front = -1;
		rear = -1;
		cursor = -1;
		data = new Passenger[CAPACITY];// is this how I should initialize? yes
		count = 1;
	}

	public BoardingQueue(int front, int rear, int size) { // do I need capacity in this?
		this.front = front;
		this.rear = rear;
		this.size = 0;
	}

	// isEmpty method
	public boolean isEmpty() {
		return (front == -1);
	}

	// isFull method
	public boolean isFull() {
		return ((rear + 1) % CAPACITY == front);
	}

	public int getId() {
		return count++;
	}

	// number of people in Q: rear+1
	public int getQPassengerCount() {
		return (rear + 1);
	}

	// enqueue method add at rear

	public void enqueuePassenger(Passenger newPass) throws NoRoomException {
		if ((rear + 1) % CAPACITY == front) {
			throw new NoRoomException();
		}
		if (front == -1) {
			front = 0;
			rear = 0;
		} else {
			rear = (rear + 1) % CAPACITY;
		}
		data[rear] = newPass;
		size++;
	}

	// dequeue method remove at front
	public Passenger dequeuePassenger() throws NoPassengerException {

		// check is Empty
		if (isEmpty() == true) {
			throw new NoPassengerException();
		}

		Passenger nextPassenger = data[front]; // set passenger you want to remove to front

		cursor = 0;

		// check if only one passenger in Q, then set Q as empty
		if (front == rear) {
			front = -1;
			rear = -1;
		}

		else {
			front = (front + 1) % CAPACITY;
		}

		size--;
		return nextPassenger;

	}

}
