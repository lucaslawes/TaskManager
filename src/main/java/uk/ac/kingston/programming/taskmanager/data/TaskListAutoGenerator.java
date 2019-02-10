/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kingston.programming.taskmanager.data;

import java.time.LocalDate;
import java.util.regex.Pattern;
import uk.ac.kingston.programming.taskmanager.model.SubTask;
import uk.ac.kingston.programming.taskmanager.model.Task;
import uk.ac.kingston.programming.taskmanager.model.TaskList;

/**
 *
 * @author lucas
 */
public class TaskListAutoGenerator {
   
    public static TaskList CreateDummyTaskList() {

        String[] sentences = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Nulla porttitor massa id neque. Maecenas pharetra convallis posuere morbi leo urna molestie at elementum. Diam quam nulla porttitor massa id. Egestas quis ipsum suspendisse ultrices gravida dictum fusce ut placerat. Vel quam elementum pulvinar etiam. At auctor urna nunc id cursus. Pellentesque pulvinar pellentesque habitant morbi tristique senectus et netus et. Suspendisse interdum consectetur libero id faucibus nisl. Proin libero nunc consequat interdum. Faucibus scelerisque eleifend donec pretium. In fermentum posuere urna nec tincidunt praesent semper. Nunc faucibus a pellentesque sit amet porttitor eget dolor morbi. Morbi quis commodo odio aenean sed adipiscing diam donec. Nec feugiat nisl pretium fusce id velit ut tortor. Dui sapien eget mi proin sed libero enim sed faucibus. Egestas quis ipsum suspendisse ultrices gravida dictum. Tellus elementum sagittis vitae et. Diam sit amet nisl suscipit adipiscing bibendum est ultricies. Pellentesque massa placerat duis ultricies lacus sed turpis tincidunt. Duis convallis convallis tellus id. Lobortis scelerisque fermentum dui faucibus in ornare quam viverra. Magna ac placerat vestibulum lectus mauris ultrices. Neque sodales ut etiam sit. Aliquet lectus proin nibh nisl.".replace(",", "").split(Pattern.quote("."));
        String[] subSentences = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Aenean vel elit scelerisque mauris pellentesque pulvinar. At volutpat diam ut venenatis tellus in. Est velit egestas dui id ornare arcu odio ut. Cras adipiscing enim eu turpis egestas pretium. Sem et tortor consequat id porta. Fringilla ut morbi tincidunt augue interdum velit euismod in pellentesque. In ante metus dictum at tempor commodo ullamcorper. Posuere lorem ipsum dolor sit amet consectetur. Odio ut enim blandit volutpat maecenas volutpat blandit. Adipiscing bibendum est ultricies integer quis auctor elit sed. Sed felis eget velit aliquet sagittis id consectetur. Phasellus vestibulum lorem sed risus ultricies. Sed lectus vestibulum mattis ullamcorper velit sed ullamcorper. Semper feugiat nibh sed pulvinar proin gravida hendrerit. Dictum fusce ut placerat orci nulla pellentesque dignissim enim sit. Bibendum neque egestas congue quisque egestas diam in arcu cursus. Convallis posuere morbi leo urna molestie at elementum eu facilisis. Neque ornare aenean euismod elementum nisi quis eleifend quam adipiscing. Quam lacus suspendisse faucibus interdum posuere lorem ipsum. Praesent elementum facilisis leo vel fringilla. Tellus molestie nunc non blandit massa enim nec dui. Magna ac placerat vestibulum lectus mauris ultrices eros in. In ornare quam viverra orci sagittis eu volutpat odio facilisis. Nisl nunc mi ipsum faucibus vitae. Varius duis at consectetur lorem donec massa. Sit amet purus gravida quis. Egestas maecenas pharetra convallis posuere morbi leo. Odio pellentesque diam volutpat commodo sed egestas egestas fringilla. Vitae auctor eu augue ut lectus. Tellus molestie nunc non blandit massa. Vulputate mi sit amet mauris commodo. Fames ac turpis egestas integer. Euismod in pellentesque massa placerat duis ultricies lacus. Nunc eget lorem dolor sed viverra ipsum nunc aliquet bibendum. Justo donec enim diam vulputate ut pharetra sit amet. Fermentum dui faucibus in ornare quam. In cursus turpis massa tincidunt dui ut ornare. Morbi blandit cursus risus at ultrices mi tempus. Neque sodales ut etiam sit. Lobortis elementum nibh tellus molestie nunc non blandit massa. At tellus at urna condimentum mattis. Vitae nunc sed velit dignissim sodales. Nibh praesent tristique magna sit amet purus. Malesuada fames ac turpis egestas integer eget aliquet. Eu mi bibendum neque egestas congue quisque. Viverra maecenas accumsan lacus vel facilisis volutpat est. Molestie a iaculis at erat pellentesque adipiscing commodo elit at. Mi quis hendrerit dolor magna eget est lorem ipsum dolor. Nec ullamcorper sit amet risus nullam. Nisi quis eleifend quam adipiscing vitae. Semper auctor neque vitae tempus quam pellentesque nec nam aliquam. Quisque sagittis purus sit amet volutpat consequat mauris nunc. Ut ornare lectus sit amet est placerat. Ullamcorper morbi tincidunt ornare massa eget. Enim nec dui nunc mattis enim ut tellus elementum. Nulla posuere sollicitudin aliquam ultrices. Sagittis nisl rhoncus mattis rhoncus urna neque. Nunc vel risus commodo viverra. Ipsum dolor sit amet consectetur adipiscing elit ut. Malesuada proin libero nunc consequat interdum varius. Id diam maecenas ultricies mi eget. Metus vulputate eu scelerisque felis imperdiet proin fermentum leo. At consectetur lorem donec massa. Amet venenatis urna cursus eget nunc scelerisque viverra. Vestibulum sed arcu non odio euismod lacinia at quis. Feugiat nisl pretium fusce id velit ut tortor. Tempus imperdiet nulla malesuada pellentesque elit. Nisl pretium fusce id velit ut tortor. Dictum non consectetur a erat nam. Est lorem ipsum dolor sit. Orci sagittis eu volutpat odio facilisis mauris sit amet massa. Neque gravida in fermentum et sollicitudin ac. In eu mi bibendum neque egestas. Suspendisse in est ante in nibh mauris cursus mattis molestie.".replace(",", "").split(Pattern.quote("."));    
        String[] names = new String[] {"Olivia", "Amelia", "Isla", "Emily", "Ava", "Lily", "Mia", "Sophia", "Isabella", "Grace", "Oliver", "Harry", "Jack", "George", "Noah", "Charlie", "Jacob", "Alfie", "Freddie", "Oscar"};
        
        TaskList taskList = new TaskList();
        
        for(int t = 1; t < sentences.length; t++) {
            
            LocalDate targetDate;
            if(((int)(Math.random() * 2) + 1) == 1) {
                targetDate = LocalDate.now().minusDays(((int)(Math.random() * 60) + 1));
            }
            else {
                targetDate = LocalDate.now().plusDays(((int)(Math.random() * 60) + 1));
            }
            
            Task task = new Task(t+1, getSentence(sentences[t], ((int)(Math.random() * 5) + 1)), names[((int)(Math.random() * names.length-1) + 1)], targetDate);
            task.setCompleted(((int)(Math.random() * 2) + 1) == 1);
            task.setPriority(((int)(Math.random() * 3) + 1));
            int numberOfSubtasks = ((int)(Math.random() * 8) + 1);
            for(int s = 0; s < numberOfSubtasks; s++) {
                task.getSubTasks().add(new SubTask(s+1, getSentence(subSentences[((int)(Math.random() * 74) + 1)], ((int)(Math.random() * 5) + 1)), s+1));
            }
            taskList.addTask(task);
        }
        
        return taskList;
    }
    
    private static String getSentence(String sentence, int maxNumberOfWords) {

        String[] words = sentence.split(" ");
        
        int numberOfWords = words.length >= maxNumberOfWords ? words.length-1 : maxNumberOfWords-1;
        
        String newSentence = "";
        
        for(int i = 0; i < numberOfWords; i++) {
            newSentence += i == 0 ? "" : " ";
            newSentence += words[i];
        }
        return newSentence;
    }
}
