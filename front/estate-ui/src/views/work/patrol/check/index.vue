<template>
  <div class="app-container">
    <WorkOrderAudit :orderType="orderType"
                    :adoptPermission="adoptPermission"
                    :rejectPermission="rejectPermission"
    ></WorkOrderAudit>
  </div>
</template>

<script>
import WorkOrderAudit from "@/components/work/workOrderAudit";

export default {
  components: {
    WorkOrderAudit
  },
  data() {
    return {
      orderType: "0",
      adoptPermission: "patrol:audit:adopt",
      rejectPermission: "patrol:audit:reject"
    };
  }
};
</script>
<style scoped>
</style>
