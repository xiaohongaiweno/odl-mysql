/*
 * Copyright © 2016 dengxiaohong and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.example.impl;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.example.impl.service.StudentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleProvider {

	private static final Logger LOG = LoggerFactory.getLogger(ExampleProvider.class);

	private final DataBroker dataBroker;

	private StudentServiceImpl studentServiceImpl;

	public ExampleProvider(final DataBroker dataBroker, StudentServiceImpl studentServiceImpl) {
		this.dataBroker = dataBroker;

		this.studentServiceImpl = studentServiceImpl;
	}

	/**
	 * Method called when the blueprint container is created.
	 */
	public void init() {
		LOG.info("ExampleProvider Session Initiated");

		studentServiceImpl.add();
		studentServiceImpl.getAll();
		studentServiceImpl.get();
		studentServiceImpl.update();
		studentServiceImpl.getAll();
		studentServiceImpl.delete();
		studentServiceImpl.getAll();
		studentServiceImpl.rollBack();
		studentServiceImpl.getAll();
		studentServiceImpl.hqlQuery();
		studentServiceImpl.getAll();
		studentServiceImpl.pageQuery();

	}

	/**
	 * Method called when the blueprint container is destroyed.
	 */
	public void close() {
		LOG.info("ExampleProvider Closed");
	}
}