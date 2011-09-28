/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.mobiledevicerules.action;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.struts.PortletAction;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Edward Han
 */
public class SiteResourceAction extends PortletAction {

	@Override
	public void serveResource(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		long actionGroupId = ParamUtil.getLong(
			resourceRequest, "actionGroupId");
		long actionLayoutId = ParamUtil.getLong(
			resourceRequest, "actionLayoutId");

		resourceRequest.setAttribute("actionGroupId", actionGroupId);
		resourceRequest.setAttribute("actionLayoutId", actionLayoutId);

		PortletContext portletContext = portletConfig.getPortletContext();

		PortletRequestDispatcher portletRequestDispatcher =
			portletContext.getRequestDispatcher(_SITE_URL_ACTION_LAYOUTS_JSP);

		portletRequestDispatcher.include(resourceRequest, resourceResponse);
	}

	private static final String _SITE_URL_ACTION_LAYOUTS_JSP =
		"/html/portlet/mobile_device_rules_admin/action/" +
			"site_url_action_layouts.jsp";

}