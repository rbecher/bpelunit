package org.bpelunit.toolsupport.util.schema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.wsdl.Definition;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;
import javax.xml.namespace.QName;

import org.bpelunit.toolsupport.util.schema.nodes.ComplexType;
import org.bpelunit.toolsupport.util.schema.nodes.Element;
import org.bpelunit.toolsupport.util.schema.nodes.SimpleType;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for an RPC/literal operation. Make sure the elements serialize
 * all parts correctly and in the right order.
 * 
 * @author Antonio García-Domínguez
 * @version 1.0
 */
public class RPCLiteralTest {

	private static final String WSDL_PATH = "testSchemata/rpcLiteralTest.wsdl";
	private static final String WSDL_TYPES_NAMESPACE = "http://www.example.org/rpcLiteralTest/Types";
	private static final String XSD_NAMESPACE = "http://www.w3.org/2001/XMLSchema";
	private static final String WSDL_OPERATION = "doSomething";
	private static final String WSDL_PORT = "rpcLiteralTestSOAP";
	private static final String WSDL_SERVICE_NAME = "rpcLiteralTest";
	private static final String WSDL_SERVICE_NAMESPACE = "http://www.example.org/rpcLiteralTest/";
	private static final String WSDL_INPUT_NAMESPACE = WSDL_SERVICE_NAMESPACE + "Input";
	private WSDLParser fParser;

	@Before
	public void setUp() throws Exception {
		WSDLFactory factory = WSDLFactory.newInstance();
		WSDLReader reader = factory.newWSDLReader();
		Definition definition = reader.readWSDL(null, WSDL_PATH);
		this.fParser = new WSDLParser(definition);
	}

	@Test
	public void generatedInputElementIsCorrect() throws Exception {
		Element wrapperElement = fParser.getInputElementForOperation(new QName(
				WSDL_SERVICE_NAMESPACE, WSDL_SERVICE_NAME), WSDL_PORT, WSDL_OPERATION);

		// The WSDL has been designed to use different namespaces for the
		// service, its soap:body binding and some of its types, to handle
		// every case. This is to ensure compliance of the WS-I Basic Profile
		// 1.1, particularly its section 4.7.10. See:
		//
		// http://www.ws-i.org/Profiles/BasicProfile-1.1.html#Namespaces_for_soapbind_Elements

		// Check the name of the wrapper element
		assertEquals("main element should be the usual RPC wrapper", new QName(
				WSDL_INPUT_NAMESPACE, WSDL_OPERATION), wrapperElement.getQName());

		// Check the contents of the wrapper element
		ComplexType wrapperType = wrapperElement.getType().getAsComplexType();
		assertNotNull("main element should have complex content", wrapperType);

		// Check the elements which serialized each part, and make sure they're
		// in the right order (see WS-I Basic 1.1, section 4.5.1)
		String[] expectedLocalParts = new String[] { "description", "amount", "currentDate",
				"fingers" };
		QName[] expectedTypes = new QName[] { new QName(XSD_NAMESPACE, "string"),
				new QName(XSD_NAMESPACE, "float"), new QName(XSD_NAMESPACE, "dateTime"),
				new QName(WSDL_TYPES_NAMESPACE, "numberOfFingers") };
		List<Element> wrapperPartElements = wrapperType.getElements();
		assertEquals("there should be as many child elements as parts in the message",
				expectedTypes.length, wrapperPartElements.size());
		for (int iElement = 0; iElement < wrapperPartElements.size(); ++iElement) {
			Element partElement = wrapperPartElements.get(iElement);

			// Check the name of the part element
			assertEquals("elements should be in the soap:body binding's namespace",
					WSDL_INPUT_NAMESPACE, partElement.getNamespace());
			assertEquals("element names should match the parts in the WSDL",
					expectedLocalParts[iElement], partElement.getLocalPart());

			SimpleType partType = partElement.getType().getAsSimpleType();
			assertNotNull("part " + expectedLocalParts[iElement] + " should have simple content",
					partType);

			assertEquals("part " + expectedLocalParts[iElement] + " should have the right type",
					expectedTypes[iElement], partType.getQName());
		}
	}

	/**
	 * This test checks that the generator can cope with operations that do not
	 * fully follow the WS-I Basic Profile 1.1, section 4.7.10, restriction R2717.
	 * The soap:body element for the <output> doesn't have a namespace attribute
	 * in this case.
	 */
	@Test
	public void generatedOutputElementIsCorrect() throws Exception {
		final QName serviceName = new QName(WSDL_SERVICE_NAMESPACE, WSDL_SERVICE_NAME);
		Element wrapperElement = fParser.getOutputElementForOperation(
				serviceName, WSDL_PORT, WSDL_OPERATION);
		assertEquals("wrapper element should have the right name",
				new QName(WSDL_SERVICE_NAMESPACE, WSDL_OPERATION),
				wrapperElement.getQName());

		ComplexType wrapperType = wrapperElement.getType().getAsComplexType();
		assertNotNull("wrapper element should have complex content", wrapperType);

		List<Element> elements = wrapperType.getElements();
		assertEquals("wrapper element should have as many children as parts in the message",
				1, elements.size());

		Element partElement = elements.get(0);
		assertEquals("part element should use the service NS and part name for its QName",
				new QName(WSDL_SERVICE_NAMESPACE, "result"), partElement.getQName());

		SimpleType partType = partElement.getType().getAsSimpleType();
		assertNotNull("part type should have simple content", partType);
		assertEquals("part type should be the right one",
				new QName(XSD_NAMESPACE, "string"),
				partType.getQName());
	}
}