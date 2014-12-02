Exemple 10.3 : fontdialog.xml

<?xml version="1.0"?>
<!DOCTYPE gridbag SYSTEM "gridbag.dtd">
<gridbag>
  <row>
    <cell anchor="EAST">
      <bean>
        <class>javax.swing.JLabel</class>
        <property>
          <name>text</name>
          <value><string>Face: </string></value>
        </property>
      </bean>
    </cell>
    <cell fill="HORIZONTAL" weightx="100">
      <bean id="face">
        <class>javax.swing.JComboBox</class>
      </bean>
    </cell>
    <cell gridheight="4" fill="BOTH" weightx="100" weighty="100">
      <bean id="sample">
        <class>javax.swing.JTextArea</class>
        <property>
          <name>text</name>
          <value><string>The quick brown fox jumps over the lazy 
   dog</string></value>
        </property>
        <property>
          <name>editable</name>
          <value><boolean>false</boolean></value>
        </property>
        <property>
          <name>lineWrap</name>
          <value><boolean>true</boolean></value>
        </property>
        <property>
          <name>border</name>
          <value>
            <bean>
              <class>javax.swing.border.EtchedBorder</class>
            </bean>
          </value>
        </property>
      </bean>
    </cell>
  </row>
  <row>
    <cell anchor="EAST">
      <bean>
        <class>javax.swing.JLabel</class>
        <property>
          <name>text</name>
          <value><string>Size: </string></value>
        </property>
      </bean>
    </cell>
    <cell fill="HORIZONTAL" weightx="100">
      <bean id="size">
        <class>javax.swing.JComboBox</class>
      </bean>
    </cell>
  </row>
  <row>
    <cell gridwidth="2" weighty="100">
      <bean id="bold">
        <class>javax.swing.JCheckBox</class>
        <property>
          <name>text</name>
          <value><string>Bold</string></value>
        </property>
      </bean>
    </cell>
  </row>
  <row>
    <cell gridwidth="2" weighty="100">
      <bean id="italic">
        <class>javax.swing.JCheckBox</class>
        <property>
          <name>text</name>
          <value><string>Italic</string></value>
        </property>
      </bean>
    </cell>
  </row>
</gridbag>
