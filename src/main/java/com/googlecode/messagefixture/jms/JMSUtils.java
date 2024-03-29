/*
 * Copyright (C) 2007 by Callista Enterprise. All rights reserved.
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package com.googlecode.messagefixture.jms;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.QueueBrowser;
import javax.jms.Session;

public class JMSUtils {
	public static void closeQuitely(Connection conn, Session session, MessageConsumer consumer) {
		closeQuitely(consumer);
		closeQuitely(session);
		closeQuitely(conn);
	}

	public static void closeQuitely(Connection conn, Session session, MessageProducer producer) {
		closeQuitely(producer);
		closeQuitely(session);
		closeQuitely(conn);
	}

	public static void closeQuitely(Connection conn, Session session, QueueBrowser browser) {
		closeQuitely(browser);
		closeQuitely(session);
		closeQuitely(conn);
	}

	public static void closeQuitely(Connection connection) {
		if(connection != null) {
			try {
				connection.close();
			} catch (JMSException e) {
				// ignore
			}
		}
	}

	public static void closeQuitely(Session session) {
		if(session != null) {
			try {
				session.close();
			} catch (JMSException e) {
				// ignore
			}
		}
	}

	public static void closeQuitely(MessageConsumer consumer) {
		if(consumer != null) {
			try {
				consumer.close();
			} catch (JMSException e) {
				// ignore
			}
		}
	}

	public static void closeQuitely(MessageProducer producer) {
		if(producer != null) {
			try {
				producer.close();
			} catch (JMSException e) {
				// ignore
			}
		}
	}
	public static void closeQuitely(QueueBrowser browser) {
		if(browser != null) {
			try {
				browser.close();
			} catch (JMSException e) {
				// ignore
			}
		}
	}
}
