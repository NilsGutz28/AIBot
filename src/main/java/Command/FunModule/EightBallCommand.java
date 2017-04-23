/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Command.FunModule;

import Command.Command;
import Constants.Constants;
import Setting.Prefix;
import Main.*;
import Constants.Emoji;
import Constants.FilePath;
import Utility.AILogger;
import Utility.UtilNum;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 *
 * @author Alien Ideology <alien.ideology at alien.org>
 */
public class EightBallCommand implements Command{
    public final static String HELP = "Ask the Magic 8Ball a question!\n"
                                    + "Command Usage: `" + Prefix.getDefaultPrefix() + "8ball`\n"
                                    + "Parameter: `-h | question | null`";


    @Override
    public void help(MessageReceivedEvent e) {
        embed.setColor(Color.red);
        embed.setTitle("Miscellaneous Module", null);
        embed.addField("EightBall -Help", HELP, true);
        embed.setFooter("Command Help/Usage", Constants.I_HELP);
        embed.setTimestamp(Instant.now());

        MessageEmbed me = embed.build();
        e.getChannel().sendMessage(me).queue();
        embed.clearFields();
    }

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        String msg = e.getAuthor().getAsMention() + " " + Emoji.EIGHT_BALL + " " + eightball(e);
        if(args.length > 0 && !"-h".equals(args[0]))
        {
            if(!e.getMessage().getContent().endsWith("?"))
            {
                e.getChannel().sendMessage(e.getAuthor().getAsMention() + " " + Emoji.EIGHT_BALL + " That doesn't sounds like a question...").queue();
                return;
            }
            e.getChannel().sendMessage(msg).queue();
        }
            
        else if(args.length == 0 || "-h".equals(args[0])) 
        {
            help(e);
        }
    }

    
    public String eightball(MessageReceivedEvent e)
    {
        String respond = "", output = "";
        int totalline = 0;
        
        //Generate Random Number base on the lines in 8Ball.txt
        try {
                BufferedReader reader = new BufferedReader(new FileReader(FilePath.EightBall));
                
                while((output = reader.readLine()) != null)
                {
                    totalline++;
                }
                reader.close();
            } catch (IOException io) {
                AILogger.errorLog(io, e, this.getClass().getName(), "BufferedReader at reading line numbers");
        }
        int magic = UtilNum.randomNum(0, totalline), line = 0;
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FilePath.EightBall));

            while((respond = reader.readLine()) != null)
            {
                line++;
                if(line >= magic)
                    break;
            }
            reader.close();
                
        } catch (IOException io) {
            AILogger.errorLog(io, e, this.getClass().getName(), "BufferedReader at getting response.");
        }
        return respond;
    }
}
