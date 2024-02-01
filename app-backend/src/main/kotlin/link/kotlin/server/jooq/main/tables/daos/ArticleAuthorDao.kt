/*
 * This file is generated by jOOQ.
 */
package link.kotlin.server.jooq.main.tables.daos


import kotlin.collections.List

import link.kotlin.server.jooq.main.tables.ArticleAuthor
import link.kotlin.server.jooq.main.tables.records.ArticleAuthorRecord

import org.jooq.Configuration
import org.jooq.Record2
import org.jooq.impl.DAOImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class ArticleAuthorDao(configuration: Configuration?) : DAOImpl<ArticleAuthorRecord, link.kotlin.server.jooq.main.tables.pojos.ArticleAuthor, Record2<Long?, Long?>>(ArticleAuthor.ARTICLE_AUTHOR, link.kotlin.server.jooq.main.tables.pojos.ArticleAuthor::class.java, configuration) {

    /**
     * Create a new ArticleAuthorDao without any configuration
     */
    constructor(): this(null)

    override fun getId(o: link.kotlin.server.jooq.main.tables.pojos.ArticleAuthor): Record2<Long?, Long?> = compositeKeyRecord(o.articleId, o.kotlinerId)

    /**
     * Fetch records that have <code>article_id BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfArticleId(lowerInclusive: Long?, upperInclusive: Long?): List<link.kotlin.server.jooq.main.tables.pojos.ArticleAuthor> = fetchRange(ArticleAuthor.ARTICLE_AUTHOR.ARTICLE_ID, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>article_id IN (values)</code>
     */
    fun fetchByArticleId(vararg values: Long): List<link.kotlin.server.jooq.main.tables.pojos.ArticleAuthor> = fetch(ArticleAuthor.ARTICLE_AUTHOR.ARTICLE_ID, *values.toTypedArray())

    /**
     * Fetch records that have <code>kotliner_id BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfKotlinerId(lowerInclusive: Long?, upperInclusive: Long?): List<link.kotlin.server.jooq.main.tables.pojos.ArticleAuthor> = fetchRange(ArticleAuthor.ARTICLE_AUTHOR.KOTLINER_ID, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>kotliner_id IN (values)</code>
     */
    fun fetchByKotlinerId(vararg values: Long): List<link.kotlin.server.jooq.main.tables.pojos.ArticleAuthor> = fetch(ArticleAuthor.ARTICLE_AUTHOR.KOTLINER_ID, *values.toTypedArray())
}