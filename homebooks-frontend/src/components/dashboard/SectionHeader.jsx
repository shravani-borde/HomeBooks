function SectionHeader({
  title,
  onViewAll
}) {
  return (
    <div className="section-header">
      <h2>{title}</h2>

      <button onClick={onViewAll}>
        View All
      </button>
    </div>
  );
}

export default SectionHeader;