package addressBook;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import addressBook.AddressEntry;

/**
 * The AddressBook class contains the numerous address entries. This is can be
 * used to make multiple AddressBooks.It also contains the read, add, remove
 * ,write methods for the address entries.
 * 
 * @author Sanchit K
 */
public class AddressBook {

  List<AddressEntry> addressEntries;

  public List<AddressEntry> getAddressEntries() {
    return addressEntries;
  }

  public void setAddressEntries(List<AddressEntry> addressEntries) {
    this.addressEntries = addressEntries;
  } // Wrong as the reference is directly passed and hence they both poin to the same object. changes in one will cause a change in the other. should make a copy of the actual parameter and then assign it.

  private AddressBook() {
    this.addressEntries = new ArrayList<AddressEntry>();
  }

  /**
   * Static factory method to create an address book
   * 
   * @return returns an instance of address book
   */
  public static AddressBook createAddressBook() {
    return new AddressBook();
  }

  /**
   * To add an address entry into the address book
   * 
   * @param addressEntry
   *          accepts an addressEntry object
   */
  public void add(AddressEntry addressEntry) {
    this.addressEntries.add(addressEntry);
  }

  /**
   * To remove an address Entry from the address book
   * 
   * @param addressEntry
   *          takes the address entry to be removed as an input
   */
  public void remove(AddressEntry addressEntry) {
    this.addressEntries.remove(addressEntry);
  }

  /**
   * To search for a given address entry based on the address
   * details-name,postal address,phone etc.
   * 
   * @param data
   *          the string to be used to search the address entries
   * @return returns the address entry that has the specified string.
   */
  public AddressEntry search(String data) {
    for (AddressEntry address : this.addressEntries) {
      StringBuffer str = new StringBuffer();

      str.append(address.getName() + " " + address.getPostalAddress() + " "
          + address.getPhoneNo() + " " + address.getEmailAddress() + " "
          + address.getNote());

      if (str.indexOf(data) > -1) {
        return address;
      }
    }
    return null;
  }

  /**
   * To save the address book in the file path specified by the user. It creates
   * the file automatically.
   * 
   * @param filePath
   *          specifies the path where the data is to be saved.
   */
  @SuppressWarnings("unchecked")
  public void saveAddressBook(String filePath) {
    JSONArray array = new JSONArray();
    for (AddressEntry address : this.addressEntries) {
      JSONObject object = new JSONObject();
      object.put("name", address.getName());
      object.put("postalAddress", address.getPostalAddress());
      object.put("phoneNo", address.getPhoneNo());
      object.put("emailAddress", address.getEmailAddress());
      object.put("note", address.getNote());
      array.add(object);
    }

    try {
      File file = new File(filePath);
      file.createNewFile();
      FileWriter writer = new FileWriter(file);
      writer.write(array.toJSONString());
      writer.flush();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * To read the Address book entries from a file on to the machine.
   * 
   * @param filepath
   *          specifies the file to be read.
   */
  public void readAddressBook(String filepath) {
    JSONParser parser = new JSONParser();
    try {
      FileReader reader = new FileReader(filepath);
      Object obj = parser.parse(reader);
      JSONArray array = (JSONArray) obj;
      for (Object object : array) {
        JSONObject jsonObj = (JSONObject) object;
        String name = jsonObj.get("name").toString();
        String postalAddress = jsonObj.get("postalAddress").toString();
        String phoneNo = jsonObj.get("phoneNo").toString();
        String emailAddress = jsonObj.get("emailAddress").toString();
        String note = jsonObj.get("note").toString();
        AddressEntry address =
            new AddressEntry.Builder(name, postalAddress).phoneNo(phoneNo)
                .emailAddress(emailAddress).note(note).build();
        this.addressEntries.add(address);
      }
    } catch (IOException | ParseException e) {
      e.printStackTrace();
    }

  }

}
