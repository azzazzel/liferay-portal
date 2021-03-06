/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatalists.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.dynamicdatalists.NoSuchRecordException;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordModelImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DDLRecordPersistenceTest {
	@Before
	public void setUp() throws Exception {
		_persistence = (DDLRecordPersistence)PortalBeanLocatorUtil.locate(DDLRecordPersistence.class.getName());
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DDLRecord ddlRecord = _persistence.create(pk);

		Assert.assertNotNull(ddlRecord);

		Assert.assertEquals(ddlRecord.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DDLRecord newDDLRecord = addDDLRecord();

		_persistence.remove(newDDLRecord);

		DDLRecord existingDDLRecord = _persistence.fetchByPrimaryKey(newDDLRecord.getPrimaryKey());

		Assert.assertNull(existingDDLRecord);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDDLRecord();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DDLRecord newDDLRecord = _persistence.create(pk);

		newDDLRecord.setUuid(ServiceTestUtil.randomString());

		newDDLRecord.setGroupId(ServiceTestUtil.nextLong());

		newDDLRecord.setCompanyId(ServiceTestUtil.nextLong());

		newDDLRecord.setUserId(ServiceTestUtil.nextLong());

		newDDLRecord.setUserName(ServiceTestUtil.randomString());

		newDDLRecord.setVersionUserId(ServiceTestUtil.nextLong());

		newDDLRecord.setVersionUserName(ServiceTestUtil.randomString());

		newDDLRecord.setCreateDate(ServiceTestUtil.nextDate());

		newDDLRecord.setModifiedDate(ServiceTestUtil.nextDate());

		newDDLRecord.setDDMStorageId(ServiceTestUtil.nextLong());

		newDDLRecord.setRecordSetId(ServiceTestUtil.nextLong());

		newDDLRecord.setVersion(ServiceTestUtil.randomString());

		newDDLRecord.setDisplayIndex(ServiceTestUtil.nextInt());

		_persistence.update(newDDLRecord, false);

		DDLRecord existingDDLRecord = _persistence.findByPrimaryKey(newDDLRecord.getPrimaryKey());

		Assert.assertEquals(existingDDLRecord.getUuid(), newDDLRecord.getUuid());
		Assert.assertEquals(existingDDLRecord.getRecordId(),
			newDDLRecord.getRecordId());
		Assert.assertEquals(existingDDLRecord.getGroupId(),
			newDDLRecord.getGroupId());
		Assert.assertEquals(existingDDLRecord.getCompanyId(),
			newDDLRecord.getCompanyId());
		Assert.assertEquals(existingDDLRecord.getUserId(),
			newDDLRecord.getUserId());
		Assert.assertEquals(existingDDLRecord.getUserName(),
			newDDLRecord.getUserName());
		Assert.assertEquals(existingDDLRecord.getVersionUserId(),
			newDDLRecord.getVersionUserId());
		Assert.assertEquals(existingDDLRecord.getVersionUserName(),
			newDDLRecord.getVersionUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingDDLRecord.getCreateDate()),
			Time.getShortTimestamp(newDDLRecord.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingDDLRecord.getModifiedDate()),
			Time.getShortTimestamp(newDDLRecord.getModifiedDate()));
		Assert.assertEquals(existingDDLRecord.getDDMStorageId(),
			newDDLRecord.getDDMStorageId());
		Assert.assertEquals(existingDDLRecord.getRecordSetId(),
			newDDLRecord.getRecordSetId());
		Assert.assertEquals(existingDDLRecord.getVersion(),
			newDDLRecord.getVersion());
		Assert.assertEquals(existingDDLRecord.getDisplayIndex(),
			newDDLRecord.getDisplayIndex());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DDLRecord newDDLRecord = addDDLRecord();

		DDLRecord existingDDLRecord = _persistence.findByPrimaryKey(newDDLRecord.getPrimaryKey());

		Assert.assertEquals(existingDDLRecord, newDDLRecord);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchRecordException");
		}
		catch (NoSuchRecordException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDLRecord newDDLRecord = addDDLRecord();

		DDLRecord existingDDLRecord = _persistence.fetchByPrimaryKey(newDDLRecord.getPrimaryKey());

		Assert.assertEquals(existingDDLRecord, newDDLRecord);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DDLRecord missingDDLRecord = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDDLRecord);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDLRecord newDDLRecord = addDDLRecord();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecord.class,
				DDLRecord.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("recordId",
				newDDLRecord.getRecordId()));

		List<DDLRecord> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DDLRecord existingDDLRecord = result.get(0);

		Assert.assertEquals(existingDDLRecord, newDDLRecord);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecord.class,
				DDLRecord.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("recordId",
				ServiceTestUtil.nextLong()));

		List<DDLRecord> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DDLRecord newDDLRecord = addDDLRecord();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecord.class,
				DDLRecord.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("recordId"));

		Object newRecordId = newDDLRecord.getRecordId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("recordId",
				new Object[] { newRecordId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingRecordId = result.get(0);

		Assert.assertEquals(existingRecordId, newRecordId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecord.class,
				DDLRecord.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("recordId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("recordId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DDLRecord newDDLRecord = addDDLRecord();

		_persistence.clearCache();

		DDLRecordModelImpl existingDDLRecordModelImpl = (DDLRecordModelImpl)_persistence.findByPrimaryKey(newDDLRecord.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingDDLRecordModelImpl.getUuid(),
				existingDDLRecordModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingDDLRecordModelImpl.getGroupId(),
			existingDDLRecordModelImpl.getOriginalGroupId());
	}

	protected DDLRecord addDDLRecord() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DDLRecord ddlRecord = _persistence.create(pk);

		ddlRecord.setUuid(ServiceTestUtil.randomString());

		ddlRecord.setGroupId(ServiceTestUtil.nextLong());

		ddlRecord.setCompanyId(ServiceTestUtil.nextLong());

		ddlRecord.setUserId(ServiceTestUtil.nextLong());

		ddlRecord.setUserName(ServiceTestUtil.randomString());

		ddlRecord.setVersionUserId(ServiceTestUtil.nextLong());

		ddlRecord.setVersionUserName(ServiceTestUtil.randomString());

		ddlRecord.setCreateDate(ServiceTestUtil.nextDate());

		ddlRecord.setModifiedDate(ServiceTestUtil.nextDate());

		ddlRecord.setDDMStorageId(ServiceTestUtil.nextLong());

		ddlRecord.setRecordSetId(ServiceTestUtil.nextLong());

		ddlRecord.setVersion(ServiceTestUtil.randomString());

		ddlRecord.setDisplayIndex(ServiceTestUtil.nextInt());

		_persistence.update(ddlRecord, false);

		return ddlRecord;
	}

	private DDLRecordPersistence _persistence;
}