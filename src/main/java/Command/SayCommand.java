/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Command;

import Config.Info;
import Config.Prefix;
import Main.*;
import java.awt.Color;
import java.time.Instant;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 *
 * @author Alien Ideology <alien.ideology at alien.org>
 */
public class SayCommand implements Command{
public final static  String HELP = "This command is for letting a bot say something for you.\n"
                                     + "Command Usage: `"+ Prefix.getDefaultPrefix() +"say`\n"
                                     + "Parameter: `-h | null`";
    private final EmbedBuilder embed = new EmbedBuilder();
    private final EmbedBuilder embedmsg = new EmbedBuilder();
            
    @Override
    public boolean called(String[] args, MessageReceivedEvent e) {
        return true;
    }

    @Override
    public void help(MessageReceivedEvent e) {
        embed.setColor(Color.red);
        embed.setTitle("Miscellaneous Module", null);
        embed.addField("Say -Help", HELP, true);
        embed.setFooter("Command Help/Usage", Info.I_help);
        embed.setTimestamp(Instant.now());

        MessageEmbed me = embed.build();
        e.getChannel().sendMessage(me).queue();
        embed.clearFields();
    }

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        String input = "";
        if("-h".equals(args[0])) 
        {
            help(e);
        }
        
        else if("embed".equals(args[0]))
        {
            List<User> mentionedUsers = e.getMessage().getMentionedUsers();
            int mencount = 0;
            for(int i = 1; i < args.length; i++)
            {
                if(!args[i].startsWith("@"))
                        input += args[i] + " ";
                else
                {
                    if(mentionedUsers.size()>0)
                        input += mentionedUsers.get(mencount).getAsMention() + " ";
                    mencount++;
                }
            }
            
            if(e.getChannelType() != e.getChannelType().PRIVATE)
            {
                //Delete the command message.
                e.getChannel().getHistory().retrievePast(1).queue((List<Message> messages) -> messages.forEach((Message msg2) -> 
                {
                    msg2.delete().queue();
                }));
            }
            
            embedmsg.setColor(Color.red);
            embedmsg.setAuthor("Said", null, e.getJDA().getSelfUser().getAvatarUrl());
            embedmsg.setDescription(input);
            embedmsg.setFooter("Requested by " + e.getAuthor().getName(), null);

            MessageEmbed memsg = embedmsg.build();
            e.getChannel().sendMessage(memsg).queue();
            embedmsg.clearFields();
        }
        
        else 
        {
            List<User> mentionedUsers = e.getMessage().getMentionedUsers();
            int mencount = 0;
            for(int i = 0; i < args.length; i++)
            {
                if(!args[i].startsWith("@"))
                    input += args[i] + " ";
                else
                {
                    if(mentionedUsers.size()>0)
                        input += mentionedUsers.get(mencount).getAsMention() + " ";
                    mencount++;
                }
            }
            
            if(e.getChannelType() != e.getChannelType().PRIVATE)
            {
                try {
                    //Delete the command message.
                    e.getChannel().getHistory().retrievePast(1).queue((List<Message> messages) -> messages.forEach((Message msg2) -> 
                    {
                        msg2.delete().queue();
                    }));

                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SayCommand.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            e.getChannel().sendMessage(input).queue();
            input = "";
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        
    }
    
}