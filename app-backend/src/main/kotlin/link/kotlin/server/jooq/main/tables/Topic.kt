/*
 * This file is generated by jOOQ.
 */
package link.kotlin.server.jooq.main.tables


import java.time.OffsetDateTime

import kotlin.collections.Collection
import kotlin.collections.List

import link.kotlin.server.jooq.main.Public
import link.kotlin.server.jooq.main.keys.ARTICLE_TOPIC__ARTICLE_TOPIC_TOPIC_ID_FKEY
import link.kotlin.server.jooq.main.keys.TOPIC_PKEY
import link.kotlin.server.jooq.main.keys.TOPIC__TOPIC_PARENT_ID_FKEY
import link.kotlin.server.jooq.main.tables.Article.ArticlePath
import link.kotlin.server.jooq.main.tables.ArticleTopic.ArticleTopicPath
import link.kotlin.server.jooq.main.tables.Topic.TopicPath
import link.kotlin.server.jooq.main.tables.records.TopicRecord

import org.jooq.Condition
import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Identity
import org.jooq.InverseForeignKey
import org.jooq.Name
import org.jooq.Path
import org.jooq.PlainSQL
import org.jooq.QueryPart
import org.jooq.Record
import org.jooq.SQL
import org.jooq.Schema
import org.jooq.Select
import org.jooq.Stringly
import org.jooq.Table
import org.jooq.TableField
import org.jooq.TableOptions
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class Topic(
    alias: Name,
    path: Table<out Record>?,
    childPath: ForeignKey<out Record, TopicRecord>?,
    parentPath: InverseForeignKey<out Record, TopicRecord>?,
    aliased: Table<TopicRecord>?,
    parameters: Array<Field<*>?>?,
    where: Condition?
): TableImpl<TopicRecord>(
    alias,
    Public.PUBLIC,
    path,
    childPath,
    parentPath,
    aliased,
    parameters,
    DSL.comment(""),
    TableOptions.table(),
    where,
) {
    companion object {

        /**
         * The reference instance of <code>public.topic</code>
         */
        val TOPIC: Topic = Topic()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<TopicRecord> = TopicRecord::class.java

    /**
     * The column <code>public.topic.id</code>.
     */
    val ID: TableField<TopicRecord, Long?> = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "")

    /**
     * The column <code>public.topic.parent_id</code>.
     */
    val PARENT_ID: TableField<TopicRecord, Long?> = createField(DSL.name("parent_id"), SQLDataType.BIGINT, this, "")

    /**
     * The column <code>public.topic.name</code>.
     */
    val NAME: TableField<TopicRecord, String?> = createField(DSL.name("name"), SQLDataType.VARCHAR(500).nullable(false), this, "")

    /**
     * The column <code>public.topic.description</code>.
     */
    val DESCRIPTION: TableField<TopicRecord, String?> = createField(DSL.name("description"), SQLDataType.CLOB.nullable(false), this, "")

    /**
     * The column <code>public.topic.created</code>.
     */
    val CREATED: TableField<TopicRecord, OffsetDateTime?> = createField(DSL.name("created"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false), this, "")

    /**
     * The column <code>public.topic.updated</code>.
     */
    val UPDATED: TableField<TopicRecord, OffsetDateTime?> = createField(DSL.name("updated"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false), this, "")

    /**
     * The column <code>public.topic.version</code>.
     */
    val VERSION: TableField<TopicRecord, Long?> = createField(DSL.name("version"), SQLDataType.BIGINT.nullable(false).defaultValue(DSL.field(DSL.raw("0"), SQLDataType.BIGINT)), this, "")

    private constructor(alias: Name, aliased: Table<TopicRecord>?): this(alias, null, null, null, aliased, null, null)
    private constructor(alias: Name, aliased: Table<TopicRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, null, aliased, parameters, null)
    private constructor(alias: Name, aliased: Table<TopicRecord>?, where: Condition): this(alias, null, null, null, aliased, null, where)

    /**
     * Create an aliased <code>public.topic</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>public.topic</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>public.topic</code> table reference
     */
    constructor(): this(DSL.name("topic"), null)

    constructor(path: Table<out Record>, childPath: ForeignKey<out Record, TopicRecord>?, parentPath: InverseForeignKey<out Record, TopicRecord>?): this(Internal.createPathAlias(path, childPath, parentPath), path, childPath, parentPath, TOPIC, null, null)

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    open class TopicPath : Topic, Path<TopicRecord> {
        constructor(path: Table<out Record>, childPath: ForeignKey<out Record, TopicRecord>?, parentPath: InverseForeignKey<out Record, TopicRecord>?): super(path, childPath, parentPath)
        private constructor(alias: Name, aliased: Table<TopicRecord>): super(alias, aliased)
        override fun `as`(alias: String): TopicPath = TopicPath(DSL.name(alias), this)
        override fun `as`(alias: Name): TopicPath = TopicPath(alias, this)
        override fun `as`(alias: Table<*>): TopicPath = TopicPath(alias.qualifiedName, this)
    }
    override fun getSchema(): Schema? = if (aliased()) null else Public.PUBLIC
    override fun getIdentity(): Identity<TopicRecord, Long?> = super.getIdentity() as Identity<TopicRecord, Long?>
    override fun getPrimaryKey(): UniqueKey<TopicRecord> = TOPIC_PKEY
    override fun getReferences(): List<ForeignKey<TopicRecord, *>> = listOf(TOPIC__TOPIC_PARENT_ID_FKEY)

    private lateinit var _topic: TopicPath

    /**
     * Get the implicit join path to the <code>public.topic</code> table.
     */
    fun topic(): TopicPath {
        if (!this::_topic.isInitialized)
            _topic = TopicPath(this, TOPIC__TOPIC_PARENT_ID_FKEY, null)

        return _topic;
    }

    val topic: TopicPath
        get(): TopicPath = topic()

    private lateinit var _articleTopic: ArticleTopicPath

    /**
     * Get the implicit to-many join path to the
     * <code>public.article_topic</code> table
     */
    fun articleTopic(): ArticleTopicPath {
        if (!this::_articleTopic.isInitialized)
            _articleTopic = ArticleTopicPath(this, null, ARTICLE_TOPIC__ARTICLE_TOPIC_TOPIC_ID_FKEY.inverseKey)

        return _articleTopic;
    }

    val articleTopic: ArticleTopicPath
        get(): ArticleTopicPath = articleTopic()

    /**
     * Get the implicit many-to-many join path to the
     * <code>public.article</code> table
     */
    val article: ArticlePath
        get(): ArticlePath = articleTopic().article()
    override fun `as`(alias: String): Topic = Topic(DSL.name(alias), this)
    override fun `as`(alias: Name): Topic = Topic(alias, this)
    override fun `as`(alias: Table<*>): Topic = Topic(alias.qualifiedName, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): Topic = Topic(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): Topic = Topic(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): Topic = Topic(name.qualifiedName, null)

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Condition): Topic = Topic(qualifiedName, if (aliased()) this else null, condition)

    /**
     * Create an inline derived table from this table
     */
    override fun where(conditions: Collection<Condition>): Topic = where(DSL.and(conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(vararg conditions: Condition): Topic = where(DSL.and(*conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Field<Boolean?>): Topic = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(condition: SQL): Topic = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String): Topic = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg binds: Any?): Topic = where(DSL.condition(condition, *binds))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg parts: QueryPart): Topic = where(DSL.condition(condition, *parts))

    /**
     * Create an inline derived table from this table
     */
    override fun whereExists(select: Select<*>): Topic = where(DSL.exists(select))

    /**
     * Create an inline derived table from this table
     */
    override fun whereNotExists(select: Select<*>): Topic = where(DSL.notExists(select))
}