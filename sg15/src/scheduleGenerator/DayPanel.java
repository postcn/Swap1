package scheduleGenerator;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class DayPanel extends JPanel{
	
	private Config config;
	private JCheckBox dayCheck;
	private int dayInt;
	private JList dayJobList;
	private JScrollPane dayScrollPane;
	private JTextField dayJobName;
	private JButton dayAddJob;
	private JButton dayDeleteJob;
	private JLabel dayLabel;
	private String day;
	
	public DayPanel(String daySet, Config configSet, int intSet, JList listSet, JCheckBox dayCheckSet, JScrollPane scrollSet,
			JTextField textSet, JButton addSet, JButton deleteSet, JLabel labelSet)
	{
		this.config = configSet;
		this.day = daySet;
		this.dayInt = intSet;
		this.dayJobList = listSet;
		this.dayCheck = new JCheckBox();
		this.dayScrollPane = scrollSet;
		this.dayJobName = textSet;
		this.dayAddJob = addSet;
		this.dayDeleteJob = deleteSet;
		this.dayLabel = labelSet;
	}
	
	
	
	public void displayPanel()
	{
		this.dayCheck.setSelected(!this.dayCheck.isSelected());
		if(this.dayCheck.isSelected()) {
            config.numSelected++;
            if(config.firstSelection) {
                config.stretch();
            }
            config.models[dayInt] = new DefaultListModel<Object>();
            this.dayJobList.setModel(config.models[this.dayInt]);
            this.dayScrollPane.setViewportView(this.dayJobList);

            this.dayJobName.setColumns(20);

            this.dayLabel.setText("Job Name:");

            this.dayAddJob.setText("Add Job");
            this.dayAddJob.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    if(!DayPanel.this.dayJobName.getText().isEmpty()) {
                        DayPanel.this.config.models[DayPanel.this.dayInt].addElement(DayPanel.this.dayJobName.getText());
                        DayPanel.this.dayJobList.setModel(DayPanel.this.config.models[DayPanel.this.dayInt]);
                        DayPanel.this.dayJobName.setText("");
                    }
                }
            });

            this.dayDeleteJob.setText("Delete Job");
            this.dayDeleteJob.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    while(!DayPanel.this.dayJobList.isSelectionEmpty()) {
                        int n = DayPanel.this.dayJobList.getSelectedIndex();
                        DayPanel.this.config.models[DayPanel.this.dayInt].remove(n);
                    }
                    
                }
            });

            javax.swing.GroupLayout dayTabLayout = new javax.swing.GroupLayout(this);
            this.setLayout(dayTabLayout);
            dayTabLayout.setHorizontalGroup(
                dayTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(dayTabLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(this.dayScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addGroup(dayTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(dayTabLayout.createSequentialGroup()
                            .addComponent(this.dayLabel)
                            .addGroup(dayTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(dayTabLayout.createSequentialGroup()
                                    .addGap(14, 14, 14)
                                    .addComponent(this.dayAddJob))
                                .addGroup(dayTabLayout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(this.dayJobName, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addComponent(this.dayDeleteJob))
                    .addContainerGap(431, Short.MAX_VALUE))
            );
            dayTabLayout.setVerticalGroup(
                dayTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(dayTabLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(dayTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(dayTabLayout.createSequentialGroup()
                            .addGroup(dayTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(this.dayJobName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(this.dayLabel))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(this.dayAddJob)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(this.dayDeleteJob))
                        .addComponent(this.dayScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(25, Short.MAX_VALUE))
            );
            this.config.dayTabs.addTab(day, this);
        } else {
            this.config.numSelected--;
            this.config.stretch();
            this.config.dayTabs.remove(this);
        }

		
	}

}
