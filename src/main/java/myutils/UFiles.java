package myutils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

public class UFiles {
  /**
   * Write an object to a file.
   *
   * @param o    Object
   * @param path file path
   * @return returns false if failed
   */
  public static boolean writeObject(Object o, String path) {
    try (ObjectOutputStream oos = new ObjectOutputStream(new DeflaterOutputStream(new FileOutputStream(path)))) {
      oos.writeObject(o);
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  /**
   * Reads object from a file
   *
   * @param path file path
   * @return the read object
   * @throws ClassNotFoundException
   * @throws IOException
   */
  public static Object readObject(String path) throws ClassNotFoundException, IOException {
    try (ObjectInputStream ois = new ObjectInputStream(new InflaterInputStream(new FileInputStream(path)))) {
      Object ret = ois.readObject();
      return ret;
    } catch (ClassNotFoundException e) {
      throw new ClassNotFoundException();
    } catch (IOException e) {
      throw new IOException(e);
    }
  }
}
