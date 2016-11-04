package models;

public class Request {
	private String name;
	private int size;
	private int accessFrequency;

    /* Constructor
     * Initializes a Request object with a name and a size.
     */
    public Request(String name,int size) {
        this.name = name;
        this.size = size;
        this.accessFrequency = 0;
    }

    /* Returns the name of the Request
     */
    public String getName() {
        return this.name;
    }

    /* Returns the accessFrequency of the Request
     */
    public int getAccessFrequency() {
        return this.accessFrequency;
    }

    /* Increments the accessfrequency of the Request
     */
    public void incrementAccessFrequency() {
        this.accessFrequency++;
    }

    /* Return the size of the Request
     */
    public int getSize() {
        return this.size;
    }

    /* This method compare two different Request
     */
    public boolean equals(Object o) {
        if (o instanceof Request) {
            Request other = (Request)o;
            return this.getName().equals(other.getName());
        }
        else {
            return false;
        }
    }
}
