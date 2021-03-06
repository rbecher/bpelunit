Changelog
=========

-----------------------
Staged for next version
-----------------------

* _Feature_: Better error reporting for failing test cases and receive conditions.
* _Feature_: Kill lingering processes in ActiveBPEL after each test case and when the test suite is completed.
* _Feature_: Add faultcode and faultstring to the *send* and *receive* activities. These allow the user to control manually the SOAP fault code and SOAP fault to be sent.
* _Feature_: Let the user select from a list in the graphical BPTS editor whether they want to send a regular input/output, a custom fault or one of the declared faults.
* _Feature_: Generate sample messages for RPC/literal operations and faults.
* _Change_: messages for RPC/lit messages can now optionally include their wrapper element in the <data> elements of the BPTS.
* _Fix_: Avoid missing namespace nodes by dumping XML as strings using a null XSLT transform instead of internal Sun APIs.
* _Fix_: Show XML Schema parsing errors while using the BPTS editor in the Problems view.
* _Fix_: Make message generation in the BPTS editor work with XSD imports with relative URLs and imported messages which use their WSDL's XML Schema definitions.
* _Fix_: Parse correctly anonymous simple types and nested model groups for generating messages in the BPTS editor. Currently all model groups are processed as if they were <xsd:sequence>s.
* _Fix_: Make sure in the BPTS editor that all generated fault messages follow the doc/lit style, according to section 3.6 of the WS-I Basic Profile 1.1.
* _Fix_: the message for the send part in an async receive/send activity is the input for the WS, not its output.

-------------
Version 1.3.0
-------------

* _Feature_: Allow activities that are not bound to a SOAP operation.
* _Feature_: Implemented the *wait* activity and added it to the Eclipse editor.
* _Fix_: Avoid stack overflow exception.
* _Fix_: Ensure xmlns:""="" is not added to namespace list.
* _Fix_: Have an error message if the Test Suite PUT Name does not match any BPEL Process name in the deployment.
* _Fix_: Fixed Undeploy Exception for ODE Deployment.
* _Fix_: Fixed charset handling in Eclipse Editor.

--------------------------
Version 1.2.2 (2009/09/25)
--------------------------

* Missing info, sorry!
* _Fix_: Wrong cursor positioning in input fields.

--------------------------
Version 1.2.1 (2009/09/22)
--------------------------

* _Fix_: Message editor does not load message if only one port and one
  operation exists.
* _Fix_: Input fields for text nodes cuts content.

--------------------------
Version 1.2.0 (2009/09/18)
--------------------------

* No information for now, sorry!

-----------
Version 1.1
-----------

* No information for now, sorry!

-----------
Version 1.0
-----------

* Initial release, available at the [Sourceforge site](http://bpelunit.sourceforge.net).
