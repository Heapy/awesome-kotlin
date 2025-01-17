/*
 * This file is generated by jOOQ.
 */
package jooq.main.tables


import java.time.LocalDateTime
import java.util.UUID

import jooq.main.Public
import jooq.main.keys.GITHUB_AUTH_PKEY
import jooq.main.tables.records.GithubAuthRecord

import kotlin.collections.Collection

import org.jooq.Condition
import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.InverseForeignKey
import org.jooq.Name
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
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class GithubAuth(
    alias: Name,
    path: Table<out Record>?,
    childPath: ForeignKey<out Record, GithubAuthRecord>?,
    parentPath: InverseForeignKey<out Record, GithubAuthRecord>?,
    aliased: Table<GithubAuthRecord>?,
    parameters: Array<Field<*>?>?,
    where: Condition?
): TableImpl<GithubAuthRecord>(
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
         * The reference instance of <code>public.github_auth</code>
         */
        val GITHUB_AUTH: GithubAuth = GithubAuth()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<GithubAuthRecord> = GithubAuthRecord::class.java

    /**
     * The column <code>public.github_auth.id</code>.
     */
    val ID: TableField<GithubAuthRecord, UUID?> = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "")

    /**
     * The column <code>public.github_auth.user_id</code>.
     */
    val USER_ID: TableField<GithubAuthRecord, UUID?> = createField(DSL.name("user_id"), SQLDataType.UUID.nullable(false), this, "")

    /**
     * The column <code>public.github_auth.access_token</code>.
     */
    val ACCESS_TOKEN: TableField<GithubAuthRecord, String?> = createField(DSL.name("access_token"), SQLDataType.VARCHAR(1024).nullable(false), this, "")

    /**
     * The column <code>public.github_auth.expires_in</code>.
     */
    val EXPIRES_IN: TableField<GithubAuthRecord, Int?> = createField(DSL.name("expires_in"), SQLDataType.INTEGER.nullable(false), this, "")

    /**
     * The column <code>public.github_auth.refresh_token</code>.
     */
    val REFRESH_TOKEN: TableField<GithubAuthRecord, String?> = createField(DSL.name("refresh_token"), SQLDataType.VARCHAR(1024).nullable(false), this, "")

    /**
     * The column <code>public.github_auth.refresh_token_expires_in</code>.
     */
    val REFRESH_TOKEN_EXPIRES_IN: TableField<GithubAuthRecord, Int?> = createField(DSL.name("refresh_token_expires_in"), SQLDataType.INTEGER.nullable(false), this, "")

    /**
     * The column <code>public.github_auth.token_type</code>.
     */
    val TOKEN_TYPE: TableField<GithubAuthRecord, String?> = createField(DSL.name("token_type"), SQLDataType.VARCHAR(1024).nullable(false), this, "")

    /**
     * The column <code>public.github_auth.scope</code>.
     */
    val SCOPE: TableField<GithubAuthRecord, String?> = createField(DSL.name("scope"), SQLDataType.VARCHAR(1024).nullable(false), this, "")

    /**
     * The column <code>public.github_auth.created_at</code>.
     */
    val CREATED_AT: TableField<GithubAuthRecord, LocalDateTime?> = createField(DSL.name("created_at"), SQLDataType.LOCALDATETIME(6).nullable(false).defaultValue(DSL.field(DSL.raw("CURRENT_TIMESTAMP"), SQLDataType.LOCALDATETIME)), this, "")

    private constructor(alias: Name, aliased: Table<GithubAuthRecord>?): this(alias, null, null, null, aliased, null, null)
    private constructor(alias: Name, aliased: Table<GithubAuthRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, null, aliased, parameters, null)
    private constructor(alias: Name, aliased: Table<GithubAuthRecord>?, where: Condition?): this(alias, null, null, null, aliased, null, where)

    /**
     * Create an aliased <code>public.github_auth</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>public.github_auth</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>public.github_auth</code> table reference
     */
    constructor(): this(DSL.name("github_auth"), null)
    override fun getSchema(): Schema? = if (aliased()) null else Public.PUBLIC
    override fun getPrimaryKey(): UniqueKey<GithubAuthRecord> = GITHUB_AUTH_PKEY
    override fun `as`(alias: String): GithubAuth = GithubAuth(DSL.name(alias), this)
    override fun `as`(alias: Name): GithubAuth = GithubAuth(alias, this)
    override fun `as`(alias: Table<*>): GithubAuth = GithubAuth(alias.qualifiedName, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): GithubAuth = GithubAuth(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): GithubAuth = GithubAuth(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): GithubAuth = GithubAuth(name.qualifiedName, null)

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Condition?): GithubAuth = GithubAuth(qualifiedName, if (aliased()) this else null, condition)

    /**
     * Create an inline derived table from this table
     */
    override fun where(conditions: Collection<Condition>): GithubAuth = where(DSL.and(conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(vararg conditions: Condition?): GithubAuth = where(DSL.and(*conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Field<Boolean?>?): GithubAuth = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(condition: SQL): GithubAuth = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String): GithubAuth = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg binds: Any?): GithubAuth = where(DSL.condition(condition, *binds))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg parts: QueryPart): GithubAuth = where(DSL.condition(condition, *parts))

    /**
     * Create an inline derived table from this table
     */
    override fun whereExists(select: Select<*>): GithubAuth = where(DSL.exists(select))

    /**
     * Create an inline derived table from this table
     */
    override fun whereNotExists(select: Select<*>): GithubAuth = where(DSL.notExists(select))
}
