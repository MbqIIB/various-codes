Exemple 10.6 : gridbag.xsd

<xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema">

   <xsd:element name="gridbag" type="GridBagType"/>

   <xsd:element name="bean" type="BeanType"/>

   <xsd:complexType name="GridBagType">
      <xsd:sequence>
         <xsd:element name="row" type="RowType" minOccurs="0" 
            maxOccurs="unbounded"/>
      </xsd:sequence>
   </xsd:complexType>

   <xsd:complexType name="RowType">
      <xsd:sequence>
         <xsd:element name="cell" type="CellType" minOccurs="0" 
            maxOccurs="unbounded"/>
      </xsd:sequence>
   </xsd:complexType>

   <xsd:complexType name="CellType">
      <xsd:sequence>
         <xsd:element ref="bean"/>
      </xsd:sequence>
      <xsd:attribute name="gridx" type="xsd:int" use="optional"/>
      <xsd:attribute name="gridy" type="xsd:int" use="optional"/>
      <xsd:attribute name="gridwidth" type="xsd:int" 
         use="optional" default="1" />
      <xsd:attribute name="gridheight" type="xsd:int" 
         use="optional" default="1" />
      <xsd:attribute name="weightx" type="xsd:int" 
         use="optional" default="0" />
      <xsd:attribute name="weighty" type="xsd:int" 
         use="optional" default="0" />
      <xsd:attribute name="fill" use="optional" default="NONE">
        <xsd:simpleType>
          <xsd:restriction base="xsd:string">
            <xsd:enumeration value="NONE" />
            <xsd:enumeration value="BOTH" />
            <xsd:enumeration value="HORIZONTAL" />
            <xsd:enumeration value="VERTICAL" />
          </xsd:restriction>
        </xsd:simpleType>
      </xsd:attribute>
      <xsd:attribute name="anchor" use="optional" default="CENTER">
        <xsd:simpleType>
          <xsd:restriction base="xsd:string">
            <xsd:enumeration value="CENTER" />
            <xsd:enumeration value="NORTH" />
            <xsd:enumeration value="NORTHEAST" />
            <xsd:enumeration value="EAST" />
            <xsd:enumeration value="SOUTHEAST" />
            <xsd:enumeration value="SOUTH" />
            <xsd:enumeration value="SOUTHWEST" />
            <xsd:enumeration value="WEST" />
            <xsd:enumeration value="NORTHWEST" />
          </xsd:restriction>
        </xsd:simpleType>
      </xsd:attribute>
      <xsd:attribute name="ipady" type="xsd:int" 
         use="optional" default="0" />
      <xsd:attribute name="ipadx" type="xsd:int" 
         use="optional" default="0" />
   </xsd:complexType>

   <xsd:complexType name="BeanType">
      <xsd:sequence>
         <xsd:element name="class" type="xsd:string"/>
         <xsd:element name="property" type="PropertyType" 
            minOccurs="0" maxOccurs="unbounded"/>
      </xsd:sequence>
      <xsd:attribute name="id" type="xsd:ID" use="optional" />
   </xsd:complexType>

   <xsd:complexType name="PropertyType">
      <xsd:sequence>
         <xsd:element name="name" type="xsd:string"/>
         <xsd:element name="value" type="ValueType"/>
      </xsd:sequence>
   </xsd:complexType>

   <xsd:complexType name="ValueType">
      <xsd:choice>
         <xsd:element ref="bean"/>
         <xsd:element name="int" type="xsd:int"/>
         <xsd:element name="string" type="xsd:string"/>
         <xsd:element name="boolean" type="xsd:boolean"/>
      </xsd:choice>
   </xsd:complexType>
</xsd:schema>
