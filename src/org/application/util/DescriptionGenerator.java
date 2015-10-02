
package org.application.util;

import java.util.ArrayList;
import java.util.Random;

/**
 * Picks random descriptions out of available pool.
 * Available pool is limited to lowercase English alphabet characters.
 * @author E.S
 */
public class DescriptionGenerator {
    
    //pool with descriptions which will be available for random picking
    private ArrayList<String> availableDescriptions;

    public DescriptionGenerator() {
        availableDescriptions = generateListWithDescriptions();
    }

   
    
    /**
     * This method picks random description from the available pool and then removes that description from it.
     * @return random description as String or empty String if pool with descriptions is empty
     */
    public String pickRandomItem()
    {
        String randomItem = "";
        if(!availableDescriptions.isEmpty())
        {
            Random random = new Random();
            int randomIndex = random.nextInt(availableDescriptions.size());
            randomItem = availableDescriptions.get(randomIndex);
            availableDescriptions.remove(randomIndex);
        }
        return randomItem;
    }
    /*
     * This method generates pool with available descriptions
     * Note: This routine can be extended latter to generate description pools of any size
     */
    private ArrayList<String> generateListWithDescriptions() {
        ArrayList<String> listWithDescriptions = new ArrayList<String>();
       //populate list with characters from a to z
        //ASCII RANGE 97 - 122
        for(char i='a'; i<='z'; i++)
        {
            listWithDescriptions.add(Character.toString(i));
        }
        return listWithDescriptions;
    }
    
}
