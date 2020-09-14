package addressBook;

/**
 * Driver class which is used to test if the address book library performs the
 * desired functions correctly or not.
 * 
 * @author Sanchit K
 */
public class Driver {

  public static void main(String[] args) {
    AddressBook addresBook = AddressBook.createAddressBook();
    AddressEntry address1 =
        new AddressEntry.Builder("Sanchit", "Thane India").build();
    AddressEntry address2 =
        new AddressEntry.Builder("Ankit", "40 America Pennignton 07306")
            .phoneNo("718.501.7512").emailAddress("sam_2008@yahoo.com").build();
    AddressEntry address3 =
        new AddressEntry.Builder("Anuja", "40 Juhu Mariott Road 5700")
            .phoneNo("718.100.7512").note("This is Anuja's address").build();

    addresBook.add(address1);
    addresBook.add(address2);
    addresBook.add(address3);
    addresBook.remove(address3);

    AddressEntry result = addresBook.search("Ankit");
    System.out.println("Result:" + result);

    addresBook.saveAddressBook("C:/Users/Ankit K/Desktop/result.text");

    addresBook.readAddressBook("C:/Users/Ankit K/Desktop/result.text");

  }

}
