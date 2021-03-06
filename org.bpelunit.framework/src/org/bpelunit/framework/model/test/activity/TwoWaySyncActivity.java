/**
 * This file belongs to the BPELUnit utility and Eclipse plugin set. See enclosed
 * license file for more information.
 * 
 */
package org.bpelunit.framework.model.test.activity;

import java.util.ArrayList;

import org.bpelunit.framework.control.ext.IHeaderProcessor;
import org.bpelunit.framework.model.test.PartnerTrack;
import org.bpelunit.framework.model.test.data.DataCopyOperation;
import org.bpelunit.framework.model.test.data.ReceiveDataSpecification;
import org.bpelunit.framework.model.test.data.SendDataSpecification;
import org.bpelunit.framework.model.test.report.ITestArtefact;

/**
 * Abstract superclass of the two-way synchronous activites Receive/Send Synchonrous and
 * Send/Receive Synchronous.
 * 
 * @version $Id$
 * @author Philip Mayer
 * 
 */
public abstract class TwoWaySyncActivity extends Activity {

	/**
	 * Send data part
	 */
	protected SendDataSpecification fSendSpec;

	/**
	 * Receive data part
	 */
	protected ReceiveDataSpecification fReceiveSpec;

	/**
	 * Registered Header Processor (may be null).
	 */
	protected IHeaderProcessor fHeaderProcessor;

	/**
	 * Registered Mapping (may be null). Note that this really only makes sense in case of
	 * Receive/Send.
	 */
	protected ArrayList<DataCopyOperation> fMapping;


	// ********************************* Initialization ****************************

	/**
	 * Creates this activity.
	 * 
	 * @param partnerTrack the parent partner track.
	 */
	public TwoWaySyncActivity(PartnerTrack partnerTrack) {
		super(partnerTrack);
	}

	/**
	 * Initializes this activity.
	 * 
	 * @param sds the send data
	 * @param rds the receive data
	 * @param headerProcessor a header processor (may be null)
	 * @param mapping mapping data (may be null)
	 */
	public void initialize(SendDataSpecification sds, ReceiveDataSpecification rds, IHeaderProcessor headerProcessor,
			ArrayList<DataCopyOperation> mapping) {
		fSendSpec= sds;
		fReceiveSpec= rds;
		fHeaderProcessor= headerProcessor;
		fMapping= mapping;
	}

	// ***************************** Activity **************************

	@Override
	public int getActivityCount() {
		return 1;
	}

	// ************************** ITestArtefact ************************

	/**
	 * Returns the parent of this activity. This is always the partner track.
	 */
	@Override
	public ITestArtefact getParent() {
		return getPartnerTrack();
	}

}
