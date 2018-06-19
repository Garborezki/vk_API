package ru.mosobrnadzor.other.utils

import android.database.DatabaseUtils

class SQLBuilder {
    private var builder: StringBuilder
    private var insertValueCount = 0

    init {
        builder = StringBuilder()
    }

    fun clear(): SQLBuilder {
        builder = StringBuilder()
        insertValueCount = 0
        return this
    }

    override fun toString(): String {
        return builder.toString()
    }

    fun createTableStart(tableName: String): SQLBuilder {
        builder.append("create table $tableName (")
        return this
    }

    fun addTableColumn(fieldName: String, columnConfig: String): SQLBuilder {
        if (builder[builder.length - 1] != '(')
            builder.append(", ")
        builder.append("$fieldName $columnConfig")
        return this
    }

    fun endTableColumn(): SQLBuilder {
        builder.append(")")
        return this
    }

    fun addIndex(indexName: String, tableName: String, fieldName: String): SQLBuilder {
        builder.append("create index $indexName on $tableName ($fieldName)")
        return this
    }

    fun insertOrIgnoreInto(tableName: String, vararg columns: String): SQLBuilder {
        var firstTime = true
        builder.append("insert or ignore into $tableName (")

        for (column in columns) {
            if (firstTime)
                firstTime = false
            else
                builder.append(",")

            builder.append(column)
        }

        builder.append(")")

        return this
    }

    fun insertOrReplaceInto(tableName: String, vararg columns: String): SQLBuilder {
        var firstTime = true
        builder.append("insert or replace into $tableName (")

        for (column in columns) {
            if (firstTime)
                firstTime = false
            else
                builder.append(",")

            builder.append(column)
        }

        builder.append(")")

        return this
    }

    fun select(vararg columns: String): SQLBuilder {
        builder.append(" select")
        var firstTime = true

        if (columns.isEmpty()) builder.append(" *")

        for (column in columns) {
            if (firstTime) {
                firstTime = false
                builder.append(" ")
            } else
                builder.append(",")

            builder.append(column)
        }

        return this
    }

    fun selectCount(field: String = "*"): SQLBuilder {
        builder.append(" select count($field) as count")
        return this
    }

    fun selectMax(field: String): SQLBuilder {
        builder.append(" select max($field) as $field")
        return this
    }

    fun selectMin(field: String): SQLBuilder {
        builder.append(" select min($field) as $field")
        return this
    }

    fun distinct(field: String): SQLBuilder {
        builder.append(" distinct $field")
        return this
    }

    fun inSelect(field: String, select: String): SQLBuilder {
        builder.append(" $field in ($select)")
        return this
    }

    fun delete(): SQLBuilder {
        builder.append("delete")
        return this
    }

    fun update(tableName: String): SQLBuilder {
        builder.append("update $tableName")
        return this
    }

    operator fun set(fieldTo: String, fieldFrom: String): SQLBuilder {
        builder.append(" set $fieldTo=$fieldFrom")
        return this
    }

    fun set(params: Map<String, Any>): SQLBuilder {
        builder.append(" set ")
        var firstTime = true
        var value: Any

        for (field in params.keys) {
            if (!firstTime)
                builder.append(",")
            else
                firstTime = false

            value = params[field]!!

            builder.append(field).append("=")

            when (value) {
                is String -> builder.append(DatabaseUtils.sqlEscapeString(value))
                is Boolean -> builder.append(if (value) 1 else 0)
                else -> builder.append(value)
            }
        }

        return this
    }

    fun from(tableName: String): SQLBuilder {
        builder.append(" from $tableName")
        return this
    }

    fun `as`(alias: String): SQLBuilder {
        builder.append(" as $alias")
        return this
    }

    fun leftJoin(table: String): SQLBuilder {
        builder.append(" left join $table")
        return this
    }

    fun where(): SQLBuilder {
        builder.append(" where")
        return this
    }

    fun `in`(field: String, items: String): SQLBuilder {
        builder.append(" $field in $items")

        return this
    }

    fun on(field1: String, field2: String): SQLBuilder {
        builder.append(" on $field1=$field2")
        return this
    }

    fun eq(field: String, value: Any) = condition(field, value, "=")

    fun nt(field: String, value: Any) = condition(field, value, "<>")

    fun greater(field: String, value: Any) = condition(field, value, ">")

    fun less(field: String, value: Any )= condition(field, value, "<")

    private fun condition(field: String, value: Any, operator: String): SQLBuilder {
        builder.append(" $field$operator")

        when (value) {
            is String -> builder.append(DatabaseUtils.sqlEscapeString(value))
            is Boolean -> builder.append(if (value) 1 else 0)
            else -> builder.append(value)
        }

        return this
    }

    fun and(): SQLBuilder {
        builder.append(" and")
        return this
    }

    fun or(): SQLBuilder {
        builder.append(" or")
        return this
    }

    fun isNull(field: String): SQLBuilder {
        builder.append(" $field is null")
        return this
    }

    fun isNotNull(field: String): SQLBuilder {
        builder.append(" $field is not null")
        return this
    }

    fun isEmpty(field: String): SQLBuilder {
        builder.append(" $field=''")
        return this
    }

    fun isNotEmpty(field: String): SQLBuilder {
        builder.append(" $field<>''")
        return this
    }

    fun orderBy(field: String): SQLBuilder {
        builder.append(" order by $field")
        return this
    }

    fun groupBy(field: String): SQLBuilder {
        builder.append(" group by $field")
        return this
    }

    fun desc(): SQLBuilder {
        builder.append(" desc")
        return this
    }

    fun asc(): SQLBuilder {
        builder.append(" asc")
        return this
    }

    fun limit(count: Int): SQLBuilder {
        builder
                .append(" limit $count")
        return this
    }

    fun like(field: String, query: String): SQLBuilder {
        builder.append(" $field like ")
                .append(DatabaseUtils.sqlEscapeString("%$query%"))
        return this
    }

    fun values(): SQLBuilder {
        builder.append(" values")
        return this
    }

    fun value(vararg items: Any): SQLBuilder {
        var firstTime = true
        if (insertValueCount != 0)
            builder.append(",")

        builder.append("(")

        for (item in items) {
            if (firstTime)
                firstTime = false
            else
                builder.append(",")

            if (item is String)
                builder.append(DatabaseUtils.sqlEscapeString(item))
            else if (item is Boolean)
                builder.append(if (item) 1 else 0)
            else
                builder.append(item)
        }

        builder.append(")")
        insertValueCount++
        return this
    }

    fun alterTable(table: String): SQLBuilder {
        builder.append("alter table $table")
        return this
    }

    fun append(text: String): SQLBuilder {
        builder.append(text)
        return this
    }

    fun dropTable(tableName: String): SQLBuilder {
        builder.append("drop table $tableName")
        return this
    }

    fun addColumn(fieldName: String, fieldConfig: String): SQLBuilder {
        builder.append(" add column $fieldName $fieldConfig")
        return this
    }

    companion object {

        fun alias(tableName: String, fieldName: String, aliasName: String): String {
            return StringBuilder()
                    .append("$tableName.$fieldName as $aliasName").toString()
        }
    }
}
