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
    protected static int epage = 0;
    protected static final int DATA_COUNT = CSVtoGraph.records.get(0).record.length;
    private static final int MAX_DIGIT = String.valueOf(CSVtoGraph.records.size()-1).length();
    private static JButton b_file;
    private static JTextField t_page;
    private static ButtonGroup bg;
    protected static JTextField t1_diff,t2_diff;
    private static JRadioButton r_page;
    private static JRadioButton r_diff;
    private static JButton b_left,b_right;
    private static JButton b_add,b_remove,b_issue;
    private static JTextField t_epage;
    private static JRadioButton r_epage;
    protected static JLabel l_epage_max;

    public static void main(String[] args) {
        t_page = new JTextField(MAX_DIGIT);
        bg = new ButtonGroup();
        r_page = new JRadioButton();
        r_diff = new JRadioButton();
        b_file = new JButton();
        b_left = new JButton();
        b_right = new JButton();
        b_add = new JButton();
        b_remove = new JButton();
        b_issue = new JButton();
        t_epage = new JTextField(MAX_DIGIT);
        r_epage = new JRadioButton();
        l_epage_max = new JLabel("/" + 0);

        DispMenu frame = new DispMenu("DispMenu");
        frame.setVisible(true);

    }

    DispMenu(String title){

        final int rows = 5;
        setTitle(title);
        setSize(500,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(rows,1));

        JPanel panel[] = new JPanel[rows];
        for(int p=0;p<panel.length;p++){
            panel[p] = new JPanel();
        }


        b_file.setText("file selection");
        b_file.addActionListener(this);

        panel[0].setLayout(new FlowLayout(FlowLayout.CENTER));
        panel[0].add(b_file);

        r_page.setSelected(true);
        r_page.addChangeListener(this);
        JLabel l_page = new JLabel("page:");

        t_page.addActionListener(this);
        t_page.setText(String.valueOf(page));
        t_page.setHorizontalAlignment(JTextField.RIGHT);

        JLabel l_page_max = new JLabel("/" + String.valueOf(CSVtoGraph.records.size()-1));

        panel[1].setLayout(new FlowLayout(FlowLayout.CENTER));
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

        panel[2].setLayout(new FlowLayout(FlowLayout.CENTER));
        panel[2].add(r_diff);
        panel[2].add(l_diff);
        panel[2].add(t1_diff);
        panel[2].add(l_minus);
        panel[2].add(t2_diff);

        bg.add(r_page);
        bg.add(r_diff);
        bg.add(r_epage);

        r_page.addChangeListener(this);

        JLabel l_epage = new JLabel("extraction:");

        b_left.addActionListener(this);
        b_left.setText("-");
        b_right.addActionListener(this);
        b_right.setText("+");

        b_add.addActionListener(this);
        b_add.setText("add");
        b_remove.addActionListener(this);
        b_remove.setText("remove");
        b_issue.addActionListener(this);
        b_issue.setText("issue");

        t_epage.addActionListener(this);
        t_epage.setHorizontalAlignment(JTextField.RIGHT);
        t_epage.setText(String.valueOf(epage));



        panel[3].setLayout(new FlowLayout(FlowLayout.CENTER));
        panel[3].add(r_epage);
        panel[3].add(l_epage);
        panel[3].add(b_left);
        panel[3].add(t_epage);
        panel[3].add(l_epage_max);
        panel[3].add(b_right);

        panel[4].setLayout(new FlowLayout(FlowLayout.CENTER));
        panel[4].add(b_add);
        panel[4].add(b_remove);
        panel[4].add(b_issue);


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
        if(event.getSource() == b_add){
            if(mode == 1){
                CSVtoGraph.exdatas.add(CSVtoGraph.records.get(page));
            }
            if(mode == 2){
                CSVtoGraph.exdatas.add(GUIbyP5.dif_records);
            }
            l_epage_max.setText("/"+String.valueOf(CSVtoGraph.exdatas.size()));
        }
        if(event.getSource() == b_remove){
            if(CSVtoGraph.exdatas.size() > 0){
                CSVtoGraph.exdatas.remove(CSVtoGraph.exdatas.size()-1);
            }
            l_epage_max.setText("/"+String.valueOf(CSVtoGraph.exdatas.size()));
        }
        if(event.getSource() == b_issue){
            if(CSVtoGraph.exdatas.size() > 0){

                /* 卍卍卍　csv発行処理　卍卍卍 */


            }
        }
    }

    public void stateChanged(ChangeEvent e) {
        if(r_page.isSelected()){
            mode = 1;
        }else if(r_diff.isSelected()){
            mode = 2;
            GUIbyP5.createDiffData();
        }else if(r_epage.isSelected()){

        }
    }

}
