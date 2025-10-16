/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chapter1_Challenge_1_4;


/*
 Project Name: Chapter1_Challenge_1_4
 File Name: FileConfigReader.java
 Description: A robust file configuration reader that handles multiple exceptions
              gracefully and ensures safe file reading.
 Author: Musa Hussein
 Date: October 2025
*/

import java.io.*; // for File, FileReader, IOException, etc.
import java.util.Scanner;

public class FileConfigReader {

    // Custom exception for invalid config version
    static class InvalidConfigVersionException extends Exception {
        public InvalidConfigVersionException(String message) {
            super(message);
        }
    }

    public static void main(String[] args) {
        BufferedReader reader = null;

        try {
            // Step 1: Try to read from config.txt
            File configFile = new File("config.txt");
            reader = new BufferedReader(new FileReader(configFile));

            // Step 2: Read first line (config version)
            String versionLine = reader.readLine();
            if (versionLine == null) throw new IOException("Config file is empty!");

            int version = Integer.parseInt(versionLine); // may throw NumberFormatException
            System.out.println("Config version read: " + version);

            // Step 3: Validate version
            if (version < 2) {
                throw new InvalidConfigVersionException("Config version too old!");
            }

            // Step 4: Read second line (file path)
            String pathLine = reader.readLine();
            if (pathLine == null) throw new IOException("Missing file path in config.");

            File pathFile = new File(pathLine);
            if (!pathFile.exists()) {
                throw new IOException("The file path does not exist: " + pathLine);
            }

            // If all good
            System.out.println("✅ Config file and path validated successfully!");

        } 
        catch (FileNotFoundException e) {
            System.out.println("❌ Error:");
        } 
        catch (NumberFormatException e) {
            System.out.println("❌ Error:  Config version too old! ");
        } 
        catch (InvalidConfigVersionException e) {
            System.out.println("⚠️ " + e.getMessage());
        } 
        catch (IOException e) {
            System.out.println("❌ I/O Error: " + e.getMessage());
        } 
        catch (Exception e) {
            System.out.println("⚠️ Unexpected error: " + e.getMessage());
        } 
        finally {
            System.out.println("📘 Config read attempt finished.");

            // Close the reader safely
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                System.out.println("⚠️ Error closing the file reader.");
            }
        }
    }
}
