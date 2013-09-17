/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2013, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.hibernate.loader.plan2.build.internal.returns;

import org.hibernate.engine.FetchStrategy;
import org.hibernate.loader.PropertyPath;
import org.hibernate.loader.plan2.build.spi.ExpandingFetchSource;
import org.hibernate.loader.plan2.spi.BidirectionalEntityFetch;
import org.hibernate.loader.plan2.spi.EntityFetch;
import org.hibernate.loader.plan2.spi.EntityIdentifierDescription;
import org.hibernate.loader.plan2.spi.EntityReference;
import org.hibernate.loader.plan2.spi.Fetch;
import org.hibernate.loader.plan2.spi.FetchSource;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.persister.walking.spi.AssociationAttributeDefinition;
import org.hibernate.type.EntityType;

/**
 * Represents an entity fetch that is bi-directionally join fetched.
 * <p/>
 * For example, consider an Order entity whose primary key is partially made up of the Customer entity to which
 * it is associated.  When we join fetch Customer -> Order(s) and then Order -> Customer we have a bi-directional
 * fetch.  This class would be used to represent the Order -> Customer part of that link.
 *
 * @author Steve Ebersole
 */
public class BidirectionalEntityFetchImpl implements BidirectionalEntityFetch, EntityFetch {
	private final ExpandingFetchSource fetchSource;
	private final AssociationAttributeDefinition fetchedAttribute;
	private final FetchStrategy fetchStrategy;
	private final EntityReference targetEntityReference;
	private final PropertyPath propertyPath;

	public BidirectionalEntityFetchImpl(
			ExpandingFetchSource fetchSource,
			AssociationAttributeDefinition fetchedAttribute,
			FetchStrategy fetchStrategy,
			EntityReference targetEntityReference) {
		this.fetchSource = fetchSource;
		this.fetchedAttribute = fetchedAttribute;
		this.fetchStrategy = fetchStrategy;
		this.targetEntityReference = targetEntityReference;
		this.propertyPath = fetchSource.getPropertyPath().append( fetchedAttribute.getName() );
	}

	public EntityReference getTargetEntityReference() {
		return targetEntityReference;
	}

	@Override
	public FetchSource getSource() {
		return fetchSource;
	}

	@Override
	public PropertyPath getPropertyPath() {
		return propertyPath;
	}

	@Override
	public FetchStrategy getFetchStrategy() {
		return fetchStrategy;
	}

	@Override
	public EntityType getFetchedType() {
		return (EntityType) fetchedAttribute.getType();
	}

	@Override
	public boolean isNullable() {
		return fetchedAttribute.isNullable();
	}

	@Override
	public String getAdditionalJoinConditions() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String[] toSqlSelectFragments(String alias) {
		return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String getQuerySpaceUid() {
		return targetEntityReference.getQuerySpaceUid();
	}

	@Override
	public Fetch[] getFetches() {
		return FetchSource.NO_FETCHES;
	}

	@Override
	public EntityReference resolveEntityReference() {
		return this;
	}

	@Override
	public EntityPersister getEntityPersister() {
		return targetEntityReference.getEntityPersister();
	}

	@Override
	public EntityIdentifierDescription getIdentifierDescription() {
		return targetEntityReference.getIdentifierDescription();
	}
}
