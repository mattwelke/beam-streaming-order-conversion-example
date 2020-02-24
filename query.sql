-- You can copy and paste this into the BigQuery
-- web console to run this without using the BQ
-- CLI tool.
WITH
  t_attr AS (
  SELECT
    term,
    attributed AS attr,
    COUNT(*) AS num
  FROM (
    SELECT
      attr_term AS term,
      TRUE AS attributed
    FROM
      beam_custom_agg_example.order_session_attributions,
      UNNEST(attributed_search_terms) attr_term)
  GROUP BY
    term,
    attributed),
  t_non_attr AS (
  SELECT
    term,
    attributed AS attr,
    COUNT(*) AS num
  FROM (
    SELECT
      attr_term AS term,
      FALSE AS attributed
    FROM
      beam_custom_agg_example.order_session_attributions,
      UNNEST(non_attributed_search_terms) attr_term)
  GROUP BY
    term,
    attributed)
SELECT
  t_attr.term,
  ROUND(SAFE_DIVIDE(t_attr.num,
      t_attr.num + t_non_attr.num) * 100, 1) AS conversion_ratio
FROM
  t_attr
INNER JOIN
  t_non_attr
ON
  t_attr.term = t_non_attr.term
ORDER BY
  conversion_ratio DESC