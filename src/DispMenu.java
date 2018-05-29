/*import processing.core.PApplet;

public class DispMenu extends PApplet implements ColorDatas {

    protected static int page = 1;
    protected static final int DATA_COUNT = CSVtoGraph.records.get(0).record.length;
    private int mouseSelect = 0;

    public void settings(){
        size(200,200);
    }

    public void setup(){

        textAlign(CENTER,CENTER);

        rectMode(CORNERS);

        surface.setSize(600,500);

    }

    public void draw(){

        background(BLACK);

        fill(WHITE);

        textSize(24);
        text("disp:",100,100);

        if(mouseSelect == 1) fill(LIGHTBLUE);
        else                 fill(WHITE);
        rect(150,75,250,125);

        fill(BLACK);
        text(page,200,100);

    }

    public void mouseReleased(){

        if(150 < mouseX && mouseX < 250 && 75 < mouseY && mouseY < 120){
            mouseSelect = 1;
        }
        else{
            mouseSelect = 0;
        }
    }

    public void keyReleased(){

        if(mouseSelect == 1){
                if(key == BACKSPACE){
                    page = page / 10;
                }
                if(key == DELETE){
                    page = 0;
                }

                int k = key - '0';
                if(0 <= k && k <= 9){
                    page = page * 10 + k;
                    while(page > CSVtoGraph.records.size()){
                        page = Integer.parseInt((str(page)).substring(1));
                    }
                }

        }
    }


    public void background(Colors c){
        background(c.r,c.g,c.b);
    }
    public void fill(Colors c){
        fill(c.r,c.g,c.b);
    }
    public void stroke(Colors c){
        stroke(c.r,c.g,c.b);
    }
}*/

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class DispMenu extends JFrame implements ActionListener,ChangeListener{

    public static int mode = 1;
    protected static int page = 1;
    protected static final int DATA_COUNT = CSVtoGraph.records.get(0).record.length;
    private static final int MAX_DIGIT = String.valueOf(CSVtoGraph.records.size()-1).length();
    private static JButton b_file;
    private static JTextField t_page;
    private static ButtonGroup bg;
    protected static JTextField t1_diff,t2_diff;
    private static JRadioButton r_page;
    private static JRadioButton r_diff;

    public static void main(String[] args) {
        t_page = new JTextField(MAX_DIGIT);
        bg = new ButtonGroup();
        r_page = new JRadioButton();
        r_diff = new JRadioButton();
        b_file = new JButton();


        DispMenu frame = new DispMenu("DispMenu");
        frame.setVisible(true);

    }

    DispMenu(String title){

        setTitle(title);
        setSize(500,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3,1));

        JPanel panel[] = new JPanel[3];
        for(int p=0;p<panel.length;p++){
            panel[p] = new JPanel();
        }


        b_file.setText("ファイルを選択");
        b_file.addActionListener(this);

        panel[0].setLayout(new FlowLayout());
        panel[0].add(b_file);

        r_page.setSelected(true);
        r_page.addChangeListener(this);
        JLabel l_page = new JLabel("page:");

        t_page.addActionListener(this);
        t_page.setText(String.valueOf(page));
        t_page.setHorizontalAlignment(JTextField.RIGHT);

        JLabel l_page_max = new JLabel("/" + String.valueOf(CSVtoGraph.records.size()-1));

        panel[1].setLayout(new FlowLayout());
        panel[1].add(r_page);
        panel[1].add(l_page);
        panel[1].add(t_page);
        panel[1].add(l_page_max);


        r_diff.addChangeListener(this);
        JLabel l_diff = new JLabel("difference:");
        t1_diff  = new JTextField("0",MAX_DIGIT);
        t1_diff.setHorizontalAlignment(JTextField.RIGHT);
        t1_diff.addActionListener(this);

        JLabel l_minus = new JLabel("-");
        t2_diff  = new JTextField("0",MAX_DIGIT);
        t2_diff.setHorizontalAlignment(JTextField.RIGHT);
        t2_diff.addActionListener(this);

        panel[2].setLayout(new FlowLayout());
        panel[2].add(r_diff);
        panel[2].add(l_diff);
        panel[2].add(t1_diff);
        panel[2].add(l_minus);
        panel[2].add(t2_diff);

        bg.add(r_page);
        bg.add(r_diff);



        Container content = getContentPane();

        for(JPanel p : panel){
            content.add(p);
        }

    }

    public static void setPage(){
        t_page.setText(String.valueOf(page));
    }

    public void actionPerformed(ActionEvent event){
        if(event.getSource() == t_page){
            try {
                int v = Integer.parseInt(t_page.getText());
                if(0<=v && v<=CSVtoGraph.records.size()-1) page = v;
                else page = 0;
            }catch(Exception e){
            } finally{
                t_page.setText(String.valueOf(page));
            }
        }
        if(event.getSource() == t1_diff || event.getSource() == t2_diff){
            GUIbyP5.createDiffData();
        }
        if(event.getSource() == b_file) {
            JFileChooser filechooser = new JFileChooser();
            filechooser.addChoosableFileFilter(new CSVFilter());

            int selected = filechooser.showOpenDialog(this);
            if (selected == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = filechooser.getSelectedFile();
                    CSVtoGraph.ReadRecords(file.getAbsolutePath());
                }catch(IOException e){
                }
            } else if (selected == JFileChooser.CANCEL_OPTION) {
                //キャンセル
            } else if (selected == JFileChooser.ERROR_OPTION) {
                //エラー
            }
        }
    }

    public void stateChanged(ChangeEvent e) {
        if(r_page.isSelected()){
            mode = 1;
        }else if(r_diff.isSelected()){
            mode = 2;
            GUIbyP5.createDiffData();
        }
    }

}
